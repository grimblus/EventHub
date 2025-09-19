package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class CreateController {

    private final EventService service;

    @Autowired
    public CreateController(EventService service) { this.service = service; }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("event", new Event());

        return "create/index";
    }

    @PostMapping("/create")
    public String submitForm(@ModelAttribute Event event, Model model) {
        ArrayList<String> errors = new ArrayList<>();
        event.setImages(new ArrayList<>());
        event.setOwnerID(1); // Change this to get Owner ID in create event
        if (validate(event, errors)) {
            service.addEvent(event);
            model.addAttribute("success", "Successfully Added Event!");

        }
        else {
            model.addAttribute("errors", errors);
        }

        return "create/index";
    }

    private boolean validate(Event event, ArrayList<String> errors) {
        boolean valid = true;

        // Title Validation
        if (event.getTitle() == null || event.getTitle().isEmpty()) {
            errors.add("Title cannot be empty!");
            valid = false;
        }
        else if (event.getTitle().length() < 3 || event.getTitle().length() > 15) {
            errors.add("Title length must be between 3 and 25 characters!");
            valid = false;
        }

        // Description Validation
        if (event.getDescription().length() > 25) {
            errors.add("Description length must be more than 25 characters!");
            valid = false;
        }

        if (event.getLocation() == null || event.getLocation().isEmpty()) {
            errors.add("Location cannot be empty!");
            valid = false;
        }

        // Date Validation
        if (event.getDate() == null) {
            errors.add("Date cannot be empty!");
            valid = false;
        }
        else if (event.getDate().isBefore(LocalDate.now().plusDays(1).atStartOfDay())) {
            errors.add("Date cannot be today or before today!");
            valid = false;
        }

        // Location Validation

        // Ticket Price Validation
        if (!event.getTicketPrice().isEmpty() && Integer.parseInt(event.getTicketPrice()) < 0) {
            errors.add("Ticket price cannot be negative!");
            valid = false;
        }

        // Category Validation

        // Bottom two are done in the script
        // Keywords Validation
        // Tags Validation

        return valid;
    }
}
