package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.User;

@Controller
public class RSVPListController {

    private final EventParticipantsService eventParticipantsService;
    private final EventService eventService;
    @Autowired
    public RSVPListController(EventParticipantsService eventParticipantsService, EventService eventService) {
        this.eventParticipantsService = eventParticipantsService;
        this.eventService = eventService;
    }


    //runs when loading user's rsvp list
    @GetMapping("/account/rsvpList")
    public String account(Model model, HttpSession session) {
        //get the logged in user
        User user = (User) session.getAttribute("user");

        List<Integer> eventIds = eventParticipantsService.getRSVPList(user.getId());
        ArrayList<Event> rsvpList = new ArrayList<>();

        for  (Integer eventId : eventIds) {
            rsvpList.add(eventService.findEventbyID(eventId));
        }
        //check that they exist to be safe
        if (user != null) {
            //add the user's rsvplist
            model.addAttribute("rsvpList", rsvpList) ;
        }
        else {
            //add an empty array list
            model.addAttribute("rsvpList", new ArrayList<>()) ;
        }
        

        return "account/rsvpList/index";
    }
}