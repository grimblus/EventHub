package au.edu.rmit.sept.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepo;

@Controller
public class SignUpController {

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new User());

        return "signUp/index";
    }

    @PostMapping("/signUp") 
    public String submitForm(@ModelAttribute User user) {
        System.out.println("ran controller");
        UserRepo.addUser(user);
        return "welcome/index";
    }
}
