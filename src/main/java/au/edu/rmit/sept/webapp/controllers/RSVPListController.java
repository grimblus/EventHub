package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.User;

@Controller
public class RSVPListController {

    @GetMapping("/account/rsvpList")
    public String account(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            model.addAttribute("rsvpList", user.getRsvpList()) ;    
        }
        else {
            model.addAttribute("rsvpList", new ArrayList<>()) ;    

        }
        

        return "account/rsvpList/index";
    }
}