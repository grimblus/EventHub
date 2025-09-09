package au.edu.rmit.sept.webapp.controllers;
import au.edu.rmit.sept.webapp.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.edu.rmit.sept.webapp.repository.UserRepo;
import au.edu.rmit.sept.webapp.repository.EventRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("active", "users");
        model.addAttribute("users", UserRepo.getUsers());
        return "admin/index";
    }

    @GetMapping("/events")
    public String showEvents(Model model) {
        model.addAttribute("active", "events");
        model.addAttribute("events", EventRepo.getEvents());
        return "admin/index";
    }
}
