package au.edu.rmit.sept.webapp.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.EventService;

@Controller
@RequestMapping("/events")
public class EventsController {
    private EventService service;

    @Autowired
    public EventsController(EventService service) {
        this.service = service;
    }

    @GetMapping
    String showEvents(Model model) {
        Collection<Event> events = service.getAllEvents(); // Change this later
        model.addAttributes("events", events);
        return "events/index";
    }
}
