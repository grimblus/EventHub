package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.UserService;

@Controller
public class SignUpController {
    @Autowired
    UserService userService;


    @GetMapping("/signUp")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "signUp/index"; 
    }


    @PostMapping("/signUp")
    public String submitForm(@ModelAttribute User user) {
        userService.addUser(user);
        return "welcome/index";
    }

}
