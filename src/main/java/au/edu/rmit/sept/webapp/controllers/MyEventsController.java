package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.UserService;

import java.util.List;

@Controller
public class MyEventsController {
    private final UserService userService;

    @Autowired
    public MyEventsController (UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/account/myEvents")
    public String account(Model model, HttpSession session) {
        if (session.getAttribute("user") == null ) {
            return "redirect:/login";
        }

        else {
            User user = (User) session.getAttribute("user");
            List<Event> savedEvents = userService.getSavedEvents(user.getId());
            model.addAttribute("savedEvents", savedEvents);

            return "account/myEvents/index";
        }
    }
}