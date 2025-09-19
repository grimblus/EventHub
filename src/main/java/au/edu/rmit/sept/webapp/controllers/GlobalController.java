package au.edu.rmit.sept.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute("loggedIn")
    public boolean checkLoggedIn(Model model, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return false;
        } 
        else {
            model.addAttribute("user", session.getAttribute("user"));
            return true;
        }
    }
}
 