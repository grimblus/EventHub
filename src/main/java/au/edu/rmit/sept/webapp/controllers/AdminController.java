package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private EventService eventService;

    @Autowired
    public AdminController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("active", "users");
        model.addAttribute("users", userService.getUsers());
        return "admin/index";
    }

    @GetMapping("/events")
    public String showEvents(Model model) {
        model.addAttribute("active", "events");
        model.addAttribute("events", eventService.getEvents());
        return "admin/index";
    }

    @PostMapping("/toggleBanned/{id}")
    public String toggleBanned(@PathVariable Integer id) {
        userService.toggleBanned(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable Integer id) {
        eventService.deleteEventbyID(id);
        return "redirect:/admin/events";
    }
}
