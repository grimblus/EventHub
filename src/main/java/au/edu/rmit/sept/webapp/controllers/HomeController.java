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

 

  @GetMapping("/")
  public String index(Model model) {

    return "welcome/index";

  }

}