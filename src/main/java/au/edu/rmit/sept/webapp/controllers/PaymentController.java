package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.PaymentService;
import io.github.cdimascio.dotenv.Dotenv;
import au.edu.rmit.sept.webapp.services.UserService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;


@Controller
public class PaymentController {

    private EventService eventService;
    private UserService userService;
    private PaymentService paymentService;
    private EventParticipantsService eventParticipantsService;
    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy @ HH:mm");

    public PaymentController(EventService eventService, UserService userService, PaymentService paymentService, EventParticipantsService eventParticipantsService) {
        this.eventService = eventService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.eventParticipantsService = eventParticipantsService;
    }

    // create empty payment model and next 12 years when you visit page
    @GetMapping("/checkout")
    public String checkout(@RequestParam("id") int id, @RequestParam("title") String title, Model model, HttpSession session) {
        if(session.getAttribute("user") == null)  { return "redirect:/login"; }

        Event curEvent = eventService.getEvent(id, title);

        // valid card 4242424242424242
        // any 3 digits
        // any future date

        // Getting user when id through user email, stops weird issue
        // where if user creates an account and immediatly goes to buy a ticket the page 
        // crashes
        String em = (((User)session.getAttribute("user")).getEmail());
        User user = userService.findUserByEmail(em);
        //System.out.println(user.getId());
        // move to other spot
        //Dotenv dotenv = Dotenv.configure().filename("compose-vars.env").load();

        // THIS IS SO STUPID DO NOTTTT DO THIS EVER IN REAL LIFE
        String boba = "sk_test_51SDcW856QKuDJ2iodsPwBTom6zY9f6b3";
        String matcha = "1MPUykXFsnhLqP9iMHM76xFVJPSkSZbLDV39er3I1Gkaow9yMQin4ptL00Z8rQ76Et";

        Stripe.apiKey = boba + matcha;

        String returnURL = "http://localhost:8080/events";
        String imgURL = "https://cdn11.bigcommerce.com/s-042b2/product_images/uploaded_images/man-lecturing-at-university.jpg";

        StringBuilder receiptURLSB = new StringBuilder("http://localhost:8080/receipt?id=");
        receiptURLSB.append(curEvent.getId());
        receiptURLSB.append("&title=");
        receiptURLSB.append(curEvent.getTitle());
        receiptURLSB.append("&ref=");
        receiptURLSB.append("REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT));
        receiptURLSB.append("&userId=");
        receiptURLSB.append(user.getId());

        // defaults to cents, guess they use that instead of a decimal?
        Long ticketPriceL = Long.parseLong(curEvent.getTicketPrice()) * 100;

        String ticketDesc = curEvent.getDescription() + " located at " + curEvent.getLocation() + " @ " + curEvent.getDate().format(f);

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(receiptURLSB.toString())
            .setCancelUrl(returnURL)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("aud")
                            .setUnitAmount(ticketPriceL)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(curEvent.getTitle())
                                    .setDescription(ticketDesc)
                                    .addImage(imgURL)
                                    .build()
                            ).build()
                    ).build()
            )
        .build();

        try {
            Session paysession = Session.create(params);
            return "redirect:" + paysession.getUrl();
        } catch(StripeException e) {
            e.printStackTrace();
        }

        // if we get any errors, just redirect to the main page, check console for errors
        return "redirect:/";
    }

    @GetMapping("/receipt")
    public String Receipt(@RequestParam("id") int id, 
                          @RequestParam("title") String title,
                          @RequestParam("ref") String ref,
                          @RequestParam("userId") int user_id,
                          Model model) {
        
        // upon succesful purchase of a ticket, we want to display a reciept for
        // the user to confirm the payment, along with a reference number they can use
        // to enquire about the payment
        Event event = eventService.getEvent(id, title);
        User user = userService.findUserByID(user_id);

        model.addAttribute("event", event);
        model.addAttribute("user", user);
        model.addAttribute("time", event.getDate().format(f));
        model.addAttribute("boughtTime", LocalDateTime.now().format(f));
        model.addAttribute("ref", ref);
        
        Payment payment = new Payment(ref, user_id, event.getTicketPrice(), LocalDateTime.now());
        paymentService.AddPayment(payment);
        
        eventParticipantsService.addEventParticipant(event.getId(), user.getId());

        // on reaching the receipt page, add the event to the users RSVP
        user.addRsvp(event);
                        
        // let event know user can come
        event.addParticipant(user);

        return "receipt/receipt";
    }

    @PostMapping("/print")
    public void PrintReceipt(@RequestParam("eventId") int id, 
                            @RequestParam("eventTitle") String title,
                            @RequestParam("bought") String boughtTime,
                            @RequestParam("refVal") String ref,
                            Model model,
                            HttpServletResponse response) {
        
        // maybe move this to its own file for better formatting?
        PDDocument receipt = new PDDocument();
        PDPage receiptPage = new PDPage();

        Event event = eventService.getEvent(id, title);


        receipt.addPage(receiptPage);

        int offsetX = 170;
        int offsetY = 500;
        
        PDPageContentStream contentStream; 

        try{

            // header image
            PDImageXObject header = PDImageXObject.createFromFile("src/main/resources/static/images/welcome/img3.png", receipt);

            contentStream = new PDPageContentStream(receipt, receiptPage);
            contentStream.drawImage(header, 0, 600, 700, 200);

            // text
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 24 );
            
            contentStream.beginText();
            contentStream.newLineAtOffset(offsetX, offsetY);
            
            contentStream.showText("Event: " + event.getTitle());

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 12 );
            contentStream.newLineAtOffset(-120, -40);
            contentStream.showText("Event Location: " + event.getLocation());

            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Event Time: " + event.getDate().format(f));

            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Ticket Price: $" + event.getTicketPrice());
                        
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 10 );
            contentStream.newLineAtOffset(0, -45);
            contentStream.showText("Purchase Time: " + boughtTime);

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 16 );
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Reference: " + ref);
            
            
            contentStream.endText();  

            contentStream.drawImage(header, 0, 0, 700, 200);

            contentStream.close();    
        } catch(IOException e) {
            e.printStackTrace();
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=receipt-" + ref + ".pdf");

        try {
            
            receipt.save(response.getOutputStream());
            receipt.close();
            response.getOutputStream().flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }    
}
