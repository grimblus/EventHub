package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.services.BadgeService;

@Controller
public class AccountController {
    private BadgeService badgeService;

    public AccountController (BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    //runs on /account
    @GetMapping("/account")
    public String account(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //if the user is not logged in, redirect to the login page
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        else {
            //otherwise set get the logged in user and pass their details to the html
            System.out.println("found stored user");
            model.addAttribute("user", session.getAttribute("user"));

            List <Badge> allBadge = badgeService.getAllBadges();
            List <Badge> userBadge = badgeService.getBadgesByUser(user.getId());
            model.addAttribute("allBadges", allBadge);
            model.addAttribute("userBadges", userBadge);
            // if (model.addAttribute("badges") == null) {
            //     model.addAttribute("badges)", Collections.emptyList());
            // }

            //Checking user badge
            List<Badge> badges = badgeService.getBadgesByUser(user.getId());
            System.out.print("Badges for user: " + user.getId() + ": " + badges);
            model.addAttribute("badge", badges);

            //Checking all badge
            List<Badge> allBadges = badgeService.getAllBadges();
            System.out.print("All badges: " + allBadges);
            model.addAttribute("allBadge", allBadges);
        }


        return "account/index";
    }
}