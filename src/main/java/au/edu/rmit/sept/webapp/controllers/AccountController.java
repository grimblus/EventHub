package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

    @GetMapping("/account")
    public String account(Model model, HttpSession session) {
        System.out.println("account page");
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        else {
            System.out.println("found stored user");
            model.addAttribute("user", session.getAttribute("user"));
        }


        return "account/index";
    }
}