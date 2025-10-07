package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private EventService eventService;

    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            System.out.println("User in session: " + user.getEmail());
            System.out.println("Permission level: " + user.getPermissionLevel());
            System.out.println("Enum check: " + (user.getPermissionLevel() == User.permissions.ADMIN));
            return user.getPermissionLevel() == User.permissions.ADMIN;
        }
        else {
            return false;
        }
    }

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
