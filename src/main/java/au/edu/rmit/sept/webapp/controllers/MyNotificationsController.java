package au.edu.rmit.sept.webapp.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyNotificationsController {

    @GetMapping("/account/myNotifications")
    public String account(Model model, HttpSession session) {



        return "account/myNotifications/index";
    }
}