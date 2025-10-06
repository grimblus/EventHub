package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedback")
    public String page(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "feedback/index";
    }

    @PostMapping("/feedback")
    public String submit(@RequestParam("rating") Integer rating,
                         @RequestParam("comment") String comment,
                         HttpSession session,
                         RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ra.addFlashAttribute("error", "Please log in to submit feedback.");
            return "redirect:/feedback";
        }
        if (rating == null || rating < 1 || rating > 5 || comment == null || comment.isBlank()) {
            ra.addFlashAttribute("error", "Rating (1-5) and a short description are required.");
            return "redirect:/feedback";
        }
        boolean ok = feedbackService.submit(user.getId(), rating, comment);
        if (ok) {
            ra.addFlashAttribute("success", true);
        } else {
            ra.addFlashAttribute("error", "Could not save feedback. Please try again.");
        }
        return "redirect:/feedback";
    }
}