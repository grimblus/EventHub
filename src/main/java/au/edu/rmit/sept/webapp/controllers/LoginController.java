package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login/index";
    }
    @GetMapping("/login")
    public String showForm(Model model) {
        return "login/index"; 
    }

    @PostMapping("/login")
    public String loginSubmit (@RequestParam String email,
                               @RequestParam("pw") String password,
                               Model model, HttpSession session) {

        User user = userService.findUserByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No account found with that email.");
            return "login/index";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid password.");
            return "login/index";
        }
        session.setAttribute("user", user);

        return "redirect:/";
    }
}