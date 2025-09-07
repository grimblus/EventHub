package au.edu.rmit.sept.webapp.controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("message", "Super cool events here!");
    model.addAttribute("today", new Date());
    return "welcome/index";

  }

}