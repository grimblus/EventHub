package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.entity.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EventsController {
    private EventService service;

    @Autowired
    public EventsController(EventService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public String showEvents(Model model) {
        List<Event> events = service.findAll();
        model.addAttribute("events", events);
        return "events/index";
    }
}
