package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.repository.EventRepo;

@Controller
public class EventFeedController {

    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", EventRepo.getEvents());

        return "events/index";
    }
}
