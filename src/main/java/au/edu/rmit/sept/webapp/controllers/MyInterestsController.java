package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.UserInterestsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.entity.Event;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;

@Controller
public class MyInterestsController {

    private final UserInterestsService userInterestsService;

    @Autowired
    public MyInterestsController (UserInterestsService userInterestsService) {
        this.userInterestsService = userInterestsService;
    }


    @GetMapping("/account/myInterests")
    public String account(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if  (user == null) {
            return "redirect:/login";
        }
        HashSet<String> interests = userInterestsService.getUserInterests(user.getId());
        model.addAttribute("interests", interests);

        model.addAttribute("categories", Event.CATEGORIES.values());
        model.addAttribute("tags", Event.TAGS.values());
        //.values() does not return strings



        // model.addAttribute("keywords", Event.KEYWORDS.values());
        return "account/myInterests/index";
    }

    @PostMapping("/account/myInterests")
    public String submitForm(@RequestParam(name = "selectedTags", required = false) ArrayList<String> selectedTags, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userInterestsService.addUserInterests(user.getId(), selectedTags);

        return "redirect:/account/myInterests";
    } 
}