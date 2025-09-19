package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {
    //runs on /account
    @GetMapping("/account")
    public String account(Model model, HttpSession session) {
        //if the user is not logged in, redirect to the login page
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        else {
            //otherwise set get the logged in user and pass their details to the html
            System.out.println("found stored user");
            model.addAttribute("user", session.getAttribute("user"));
        }


        return "account/index";
    }
}