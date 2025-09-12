package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.repository.EventRepo;
import au.edu.rmit.sept.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
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
        model.addAttribute("events", EventRepo.getEvents());
        return "admin/index";
    }
}
