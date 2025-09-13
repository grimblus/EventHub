package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyEventsController {

    @GetMapping("/account/myEvents")
    public String account(Model model, HttpSession session) {
        return "account/myEvents/index";
    }
}