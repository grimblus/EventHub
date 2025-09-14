package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SignUpController {

    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new User());

        return "signUp/index";
    }

    @PostMapping("/signUp") 
    public String submitForm(@ModelAttribute User user, HttpSession session) {
        System.out.println("ran controllerrrr");
        userService.addUser(user);
        session.setAttribute("user", user);

        return "welcome/index";
    }

    
}
