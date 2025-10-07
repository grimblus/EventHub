package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events/{eventId}/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final EventService eventService;

    public FeedbackController(FeedbackService feedbackService, EventService eventService) {
        this.feedbackService = feedbackService;
        this.eventService = eventService;
    }

    @GetMapping
    public String page(@PathVariable int eventId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Event event = eventService.findEventbyID(eventId);

        model.addAttribute("user", user);
        model.addAttribute("event", event);

        return "feedback/event";
    }

    @PostMapping
    public String submit(@PathVariable int eventId,
                         @RequestParam("rating") Integer rating,
                         @RequestParam("comment") String comment,
                         HttpSession session,
                         RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ra.addFlashAttribute("error", "Please log in to submit feedback.");
            return "redirect:/events/" + eventId + "/feedback";
        }

        Event event = eventService.findEventbyID(eventId);
        if (event == null) {
            ra.addFlashAttribute("error", "Event not found.");
            return "redirect:/events";
        }

        String eventTitle = event.getTitle();
        if (rating == null || rating < 1 || rating > 5 || comment == null || comment.isBlank()) {
            ra.addFlashAttribute("error", "Rating (1–5) and a short description are required.");
            return "redirect:/events/" + eventId + "/feedback";
        }

        boolean ok = feedbackService.submitForEvent(user.getId(), eventId, eventTitle, rating, comment);
        if (ok) {
            ra.addFlashAttribute("success", true);
        } else {
            ra.addFlashAttribute("error", "Could not save feedback. Please try again.");
        }
        return "redirect:/events/" + eventId + "/feedback";
    }
}