package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import au.edu.rmit.sept.webapp.repository.EventRepositoryImpl;
import org.springframework.web.bind.annotation.RequestParam;
import au.edu.rmit.sept.webapp.services.EventService;
import java.util.List;

@Controller
public class EventFeedController {

    private EventService eventService;

    public EventFeedController(EventService eventService) {this.eventService = eventService;}

    @GetMapping("/events")
    public String events(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> keywords,
            Model model
    ) {
        // Find out how to do tags and stuff
        model.addAttribute("events", eventService.getEventsByFilter(search, tags, location, category, keywords));
        model.addAttribute("tags", eventService.getTags());
        model.addAttribute("keywords", eventService.getKeywords());

        return "events/index";
    }

    @PostMapping("/events")
    public String eventDetails(
            Integer id, 
            Model model
    ) {
        model.addAttribute("modal-content", eventService.findEventbyID(id));
        return "events/index";
    }
}
