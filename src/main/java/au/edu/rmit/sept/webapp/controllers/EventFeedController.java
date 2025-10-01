package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.UserInterestsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class EventFeedController {

    private EventService eventService;
    private UserInterestsService userInterestsService;
    private EventParticipantsService eventParticipantsService;

    public EventFeedController(EventService eventService, UserInterestsService userInterestsService, EventParticipantsService eventParticipantsService) {
        this.eventService = eventService;
        this.userInterestsService = userInterestsService;
        this.eventParticipantsService = eventParticipantsService;
    }

    @GetMapping("/events")
    public String events(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> keywords,
            Model model,
            HttpSession session
    ) {
        // Find out how to do tags and stuff
        User user = (User) session.getAttribute("user");
        List<Event> events =  eventService.getEventsByFilter(search, tags, location, category, keywords);

        //if user is  logged in, display recommended events
        if (user != null) {
            //hashset so that checking for an interest is O(1) time B)
            HashSet<String> userInterests = userInterestsService.getUserInterests(user.getId());
            List<Event> recommendedEvents = new ArrayList<>();

            //for each event
            for ( Event event : events) {
//                System.out.println("Event:" + event.getTitle());
//                System.out.println(event.getCategory());
                //if the event's category is the user's interest, add it it to the list to display
                if (userInterests.contains(event.getCategory().toUpperCase())) {
                    recommendedEvents.add(event);

                }
                //if didn't find a category interested in, then check the tags
                else  {
                    //for each of the event's tags
                    for ( String tag : event.getTags()) {
                       // System.out.println(tag);

                        //if the event's tag is one of the user's interest, add the event to the list
                        // and break as well because only one interest needs to be found
                        if (userInterests.contains(tag.toUpperCase())) {
                            recommendedEvents.add(event);
                            break;
                        }
                    }
                }
            }
            model.addAttribute("recommendedEvents", recommendedEvents);
        }

        model.addAttribute("events", events);
        model.addAttribute("tags", eventService.getTags());
        model.addAttribute("keywords", eventService.getKeywords());

        return "events/index";
    }

    // checks if the user is the owner of the event
    @GetMapping("/events/modal/{eventId}")
    @ResponseBody
    public Map<String, Object> checkOwner(@PathVariable Integer eventId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean isOwner = false;

        //If the owner is an organiser and the user exists, checks if user id matches with the event's owner id
        if (user != null && user.getPermissionLevel() == User.permissions.ORGANISER) {
            Integer ownerId = eventService.getOwnerID(eventId);
            if (ownerId != null && ownerId.equals(user.getId())) {
                isOwner = true;
            }
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("isOwner", isOwner);
        return resp;
    }

    // event details for modal
    @PostMapping("/events")
    public String eventDetails(
            Integer id, 
            Model model
    ) {
        model.addAttribute("modal-content", eventService.findEventbyID(id));
        return "events/index";
    }

    // delete images
    @DeleteMapping("/events/{eventId}/images/{index}")
    public ResponseEntity<Void> deleteImage(@PathVariable int eventId, @PathVariable int index) {
        return eventService.deleteImageForEvent(eventId, index)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    // add images
    @PostMapping("/events/{eventId}/images")
    public ResponseEntity<Void> addImage(@PathVariable int eventId) {
        boolean ok = eventService.addImageForEvent(eventId, "temp.png");
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // export CSV of people attending event
    @PostMapping("/events/{eventId}/export")
    public ResponseEntity<Void> exportCSV(@RequestParam("eventId") int eventId,
                                            Model model,
                                            HttpServletResponse response) {
        // function works, but button press does not....

        response.setContentType("text/csv;  charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\'users.csv'");
        
        try(PrintWriter writer = response.getWriter()) {
            writer.println("ID,Name,Email");
            System.out.println("fokfddlkflkd");
        } catch(IOException e) {
            e.printStackTrace();
        }
        return eventParticipantsService.PrintAttendeeCSV(1) 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.notFound().build();
    }

    // delete event
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventId) {
        return eventService.deleteEventbyID(eventId)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

}
