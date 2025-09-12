package au.edu.rmit.sept.webapp.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    // temp code to test getting local hostname for qr links
    try {
      InetAddress addr = InetAddress.getLocalHost();
      String hostName = addr.getHostName();    
      String redirect = "";
      String hostLink = "THIS SHOULD CONTAIN A LINK (with your computers hostname) THAT LEADS YOU TO THIS PAGE: http://" + hostName + ".local:8080/" + redirect;
      model.addAttribute("hostLink", hostLink);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    // to be moved to the event details file, as we only need to generate the hostname for the sign in qr code

    return "welcome/index";

  }

}