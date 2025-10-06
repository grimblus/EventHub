package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.PaymentRequest;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.PaymentService;
import au.edu.rmit.sept.webapp.services.SandboxPaymentGateway;
import au.edu.rmit.sept.webapp.services.SandboxPaymentGateway.Result;
import au.edu.rmit.sept.webapp.services.UserService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class PaymentController {

    private EventService eventService;
    private UserService userService;
    private PaymentService paymentService;
    private final SandboxPaymentGateway gateway;
    private EventParticipantsService eventParticipantsService;
    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy @ HH:mm");

    public PaymentController(SandboxPaymentGateway gateway, EventService eventService, UserService userService, PaymentService paymentService, EventParticipantsService eventParticipantsService) {
        this.gateway = gateway;
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
        PaymentRequest req = new PaymentRequest();

        // we have to set the ticket price on loading so that 
        // the payment request field is filled
        req.setAmount(Double.parseDouble(curEvent.getTicketPrice()));
        model.addAttribute("payment", req);

        // see nextYears helper below
        model.addAttribute("years", nextYears(12));

        // Getting user when id through user email, stops weird issue
        // where if user creates an account and immediatly goes to buy a ticket the page 
        // crashes
        String em = (((User)session.getAttribute("user")).getEmail());
        User user = userService.findUserByEmail(em);
        //System.out.println(user.getId());

        model.addAttribute("userId", user.getId());
        model.addAttribute("event", curEvent);

        String date = curEvent.getDate().format(f).toString();
        model.addAttribute("date", date);   

        return "payment/checkout";
    }

    // once the user has baught a ticket, add it to their rsvp list 
    private void addToRsvpList(Event event, HttpSession session ) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.addRsvp(event);
        }
    } 

    // events also need to know that the user will be attending the event
    private void addToEventParticipants(Event event, HttpSession session ) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            event.addParticipant(user);
        }
    } 

    // handles form submission validation check
    @PostMapping("/pay")
    public String pay(@Valid @ModelAttribute("payment") PaymentRequest req,
                      BindingResult errors,
                      @RequestParam("eventId") int eventID,
                      @RequestParam("eventTitle") String eventTitle,
                      @RequestParam("userId") int userId,
                      Model model, HttpSession session) {
        
        // Due to the form reloading the page, we have to reload the event and user objects
        // to ensure the information in the page is filled out
        Event event = eventService.getEvent(eventID, eventTitle);
        model.addAttribute("event", event);
        model.addAttribute("userId", userId);
        
        req.setAmount(Double.parseDouble(event.getTicketPrice()));

        model.addAttribute("date", event.getDate().format(f));
        model.addAttribute("years", nextYears(12));

        if (errors.hasErrors()) {
            return "payment/checkout";
        }

        Result r = gateway.charge(req);
        // now we check if the user has input valid details, sending them to the receipt page if they have
        // and returning an error message if they have not/
        if (r.success()) {
            addToRsvpList(event, session);
            addToEventParticipants(event, session);
            // on succesful payment, redirect us to the reciept page
            return "redirect:/receipt" + "?id=" + event.getId() + "&title=" + URLEncoder.encode(event.getTitle(), StandardCharsets.UTF_8) + "&ref=" + r.reference() + "&userId=" + userId;
        } else {
            return "redirect:/checkout" + "?id=" + event.getId() + "&title=" + URLEncoder.encode(event.getTitle(), StandardCharsets.UTF_8) + "&failed=1&reason=" + r.message();
        }
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

        System.out.println(id + title + ref);
            
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

    // helper that gets the next 12 years (e.g. 2025, 2026, 2027...2036) for the initial empty page
    private List<Integer> nextYears(int count) {
        int start = Year.now().getValue();
        return IntStream.range(0, count).map(i -> start + i).boxed().toList();
    }
    
}
