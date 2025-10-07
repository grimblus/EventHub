package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.UserInterestsService;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DashboardController {

    private EventService eventService;
    private EventParticipantsService eventParticipantsService;
    private UserService userService;

    public DashboardController(EventService eventService, EventParticipantsService eventParticipantsService, UserService userService) {
        this.eventService = eventService;
        this.eventParticipantsService = eventParticipantsService;
        this.userService = userService;
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        //if the user is not logged in, redirect to the login page
        if (session.getAttribute("user") == null ) {
            return "redirect:/login";
        }

        else {
            User user = (User) session.getAttribute("user");
            List<Event> events =  eventService.getEventsByOwnerId(user.getId());



            model.addAttribute("events", events);
        }
        return "dashboard/index";
    }

    @GetMapping("dashboard/{id}")
    public String eventRSVP(Model model, @PathVariable("id") int id, HttpSession session) {
         if (session.getAttribute("user") == null ) {
            return "redirect:/login";
        }

        else {
            List<User> users = eventParticipantsService.getEventParticipants(id);
            model.addAttribute("users", users);
            return "dashboard/event/index";
        }
        
    }
}
