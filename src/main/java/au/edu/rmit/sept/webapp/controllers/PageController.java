package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // specifci event page when user clicks on an event
    // will be loaded with event details
    @GetMapping("/event") 
    public String event() {
        return "event-page/index";
    }

    @GetMapping("/admin")
    public String admin() { return "admin/index"; }
}
