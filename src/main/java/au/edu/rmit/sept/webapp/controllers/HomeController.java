package au.edu.rmit.sept.webapp.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.UserService;

@Controller
public class HomeController {

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("message", "Super cool events here!");
    model.addAttribute("today", new Date());
    List<User> users = userService.getUsers();
    model.addAttribute("users", users);

    return "welcome/index.html";

  }

}