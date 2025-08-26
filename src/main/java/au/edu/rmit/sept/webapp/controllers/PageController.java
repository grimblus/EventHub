package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // @GetMapping("/")
    // public String home() {
    //     return "home/index";
    // }

    @GetMapping("/events")
    public String events() {
        return "events/index";
    }

    @GetMapping("/create")
    public String create() {
        return "create/index";
    }

    @GetMapping("/account")
    public String account() {
        return "account/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login/index";
    }
}
