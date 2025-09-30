package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class CreateController {

    private final EventService eventService;

    @Autowired
    public CreateController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/create")
    public String create(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");
        if (user.getPermissionLevel() != User.permissions.ADMIN && user.getPermissionLevel() != User.permissions.ORGANISER) {
            return "redirect:/login";
        }

        model.addAttribute("event", new Event());
        model.addAttribute("keywords", Event.KEYWORDS.values());
        model.addAttribute("tags", Event.TAGS.values());
        model.addAttribute("categories", Event.CATEGORIES.values());

        return "create/index";
    }

    @PostMapping("/create")
    public String submitForm(@ModelAttribute Event event, Model model, HttpSession session) {
        ArrayList<String> errors = new ArrayList<>();
        event.setImages(new ArrayList<>());
        event.setOwnerID(((User) session.getAttribute("user")).getId());
        if (validate(event, errors)) {
            eventService.addEvent(event);
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
        if (event.getDescription().length() > 300) {
            errors.add("Description length must be less than 300 characters!");
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
        else if (!event.getTicketPrice().isEmpty() && Integer.parseInt(event.getTicketPrice()) > 1000) {
            errors.add("Ticket price cannot exceed 1000!");
            valid = false;
        }

        // Category Validation
        if (!event.getCategory().isEmpty()) {
            boolean foundCategoryError = false;
            for (Event.CATEGORIES category : Event.CATEGORIES.values()) {
                if (event.getCategory().toUpperCase().equals(category.name())) {
                    foundCategoryError = true;
                    break;
                }
            }
            if (!foundCategoryError) {
                errors.add("Category does not exist!");
                valid = false;
            }
        }

        // Bottom two are done in the script
        // Keywords Validation
        // Tags Validation

        return valid;
    }
}
