package au.edu.rmit.sept.webapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.EventRepositoryImpl;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.services.EventService;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Model model,
            HttpSession session
    ) {
        // Find out how to do tags and stuff
        model.addAttribute("events", eventService.getEventsByFilter(search, tags, location, category, keywords));
        model.addAttribute("tags", eventService.getTags());
        model.addAttribute("keywords", eventService.getKeywords());

        return "events/index";
    }

    // @GetMapping("/events/modal")
    // public String deleteButton(Model model, 
    //                             HttpSession session,
    //                             @RequestParam Integer eventid) {

    //     //Hide delete event button unless club organiser, MUST CHANGE LATER TO CHECK WHICH ORGANISER MADE WHICH EVENT ONCE CREATE EVENT IS DONE
    //     User user = (User) session.getAttribute("user");
    //     boolean isOwner = false;
    //     // Integer inteventid = Integer.parseInt(eventid);
    //     if ((user != null) && (user.getPermissionLevel() == User.permissions.ORGANISER)) {
    //         Integer id = user.getId();
    //         Integer ownerId = eventService.getOwnerID(eventid);
    //             // System.out.println("Owner ID from EventService: " + ownerId);
    //             // System.out.println("user ID: " + id);
    //             // System.out.println("role " + user.getPermissionLevel());
    //         if (ownerId != null && (ownerId.equals(id))) {
    //             isOwner = true;
    //             // System.out.println("isOwner = " + isOwner);
    //             if (isOwner) {
    //                 model.addAttribute("loggedOrganiser", true);
    //                 System.out.println("loggedOrganiser");
    //             }
    //         }
    //     }
    //     return "events/index";
    // }

    //Checks if the user is the owner of the event when they click on the modal
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

    // delete event
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventId) {

        

        return eventService.deleteEventbyID(eventId)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

}
