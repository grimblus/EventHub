package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.PaymentRequest;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventService;

import au.edu.rmit.sept.webapp.services.SandboxPaymentGateway;
import au.edu.rmit.sept.webapp.services.SandboxPaymentGateway.Result;
import au.edu.rmit.sept.webapp.services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class PaymentController {

    // event service is NULLLLLL
    private EventService eventService;
    private UserService userService;
    private final SandboxPaymentGateway gateway;

    public PaymentController(SandboxPaymentGateway gateway, EventService eventService, UserService userService) {
        this.gateway = gateway;
        this.eventService = eventService;
        this.userService = userService;
    }

    // create empty payment model and next 12 years when you visit page
    @GetMapping("/checkout")
    public String checkout(@RequestParam("id") int id, @RequestParam("title") String title, Model model, HttpSession session) {
        System.out.println("Checking logged in");
        //if(session.getAttribute("user") == null)  { return "redirect:/login"; }

        Event curEvent = eventService.getEvent(id, title);
        PaymentRequest req = new PaymentRequest();
        req.setAmount(Double.parseDouble(curEvent.getTicketPrice()));
        model.addAttribute("payment", req);



        // see nextYears helper below
        model.addAttribute("years", nextYears(12));

        // Getting user when id is not available even though it is?
        // String em = (((User)session.getAttribute("user")).getEmail());
        // User user = userService.findUserByEmail(em);
        // System.out.println(user.getId());


        model.addAttribute("event", curEvent);

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy @ HH:mm");
        String date = curEvent.getDate().format(f).toString();

        model.addAttribute("date", date);   

        return "payment/checkout";
    }

    // handles form submission validation check
    @PostMapping("/pay")
    public String pay(@Valid @ModelAttribute("payment") PaymentRequest req,
                      BindingResult errors,
                      @RequestParam("eventId") int eventID,
                      @RequestParam("eventTitle") String eventTitle,
                      Model model) {
                        
        Event event = eventService.getEvent(eventID, eventTitle);
        model.addAttribute("event", event);
        
        req.setAmount(Double.parseDouble(event.getTicketPrice()));

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy @ HH:mm");
        model.addAttribute("date", event.getDate().format(f));
        model.addAttribute("years", nextYears(12));

        if (errors.hasErrors()) {
            return "payment/checkout";
        }

        Result r = gateway.charge(req);
        if (r.success()) {
            return "redirect:/checkout" + "?id=" + event.getId() + "&title=" + URLEncoder.encode(event.getTitle(), StandardCharsets.UTF_8) + "&success=1&ref=" + r.reference();
        } else {
            return "redirect:/checkout" + "?id=" + event.getId() + "&title=" + URLEncoder.encode(event.getTitle(), StandardCharsets.UTF_8) + "&failed=1&reason=" + r.message();
        }
    }

    // helper that gets the next 12 years (e.g. 2025, 2026, 2027...2036) for the initial empty page
    private List<Integer> nextYears(int count) {
        int start = Year.now().getValue();
        return IntStream.range(0, count).map(i -> start + i).boxed().toList();
    }
    
}
