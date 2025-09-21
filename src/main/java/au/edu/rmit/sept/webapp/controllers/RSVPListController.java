package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.User;

@Controller
public class RSVPListController {
    //runs when loading user's rsvp list
    @GetMapping("/account/rsvpList")
    public String account(Model model, HttpSession session) {
        //get the logged in user
        User user = (User) session.getAttribute("user");
        //check that they exist to be safe
        if (user != null) {
            //add the user's rsvplist
            model.addAttribute("rsvpList", user.getRsvpList()) ;    
        }
        else {
            //add an empty array list
            model.addAttribute("rsvpList", new ArrayList<>()) ;    

        }
        

        return "account/rsvpList/index";
    }
}