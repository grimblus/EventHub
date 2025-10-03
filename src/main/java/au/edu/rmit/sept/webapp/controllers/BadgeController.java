package au.edu.rmit.sept.webapp.controllers;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.services.UserService;

@Controller
public class BadgeController {
    private final UserService userService;

    public BadgeController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account/badge")
    public Set<Badge> userBadge (@PathVariable int id) {
        return userService.findUserByID(id).getBadgeID();
    }
}
