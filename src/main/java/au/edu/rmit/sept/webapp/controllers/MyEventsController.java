package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.Event;

import java.util.List;

@Controller
public class MyEventsController {

    @GetMapping("/account/myEvents")
    public String account(Model model, HttpSession session) {
        List<Event> savedEvents = null;
        model.addAttribute("savedEvents", savedEvents);

        return "account/myEvents/index";
    }
}