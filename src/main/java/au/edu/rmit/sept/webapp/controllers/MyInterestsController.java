package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.Event.CATEGORIES;
import au.edu.rmit.sept.webapp.entity.Event.TAGS;

@Controller
public class MyInterestsController {

    @GetMapping("/account/myInterests")
    public String account(Model model, HttpSession session) {

        model.addAttribute("categories", CATEGORIES.values());
        model.addAttribute("tags", Event.TAGS.values());
        // model.addAttribute("keywords", Event.KEYWORDS.values());
        return "account/myInterests/index";
    }

    @PostMapping("/account/myInterests/tags") 
    public String submitForm() {
        
        return "/account/myInterests/index";
    } 
}