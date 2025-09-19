package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.ui.Model;

@Controller
public class PageController {


    
    // @GetMapping("/")
    // public String home() {
    //     return "home/index";
    // }

//    @GetMapping("/events")
//    public String events() {
//        return "events/index";
//    }

//    @GetMapping("/create")
//    public String create() {
//        return "create/index";
//    }

//    @GetMapping("/account")
//    public String account() {
//        return "account/index";
//    }

    // @GetMapping("/login")
    // public String login() {
    //     return "login/index";
    // }

    // @GetMapping("/signUp")
    // public String signUp(Model model) {
    //     model.addAttribute("user", new User());

    //     return "signUp/index";
    // }

    // specifci event page when user clicks on an event
    // will be loaded with event details
    @GetMapping("/event") 
    public String event() {
        return "event-page/index";
    }

    @GetMapping("/admin")
    public String admin() { return "admin/index"; }
}
