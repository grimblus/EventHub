package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class CreateController {

    private final EventService service;

    @Autowired
    public CreateController(EventService service) { this.service = service; }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("event", new Event());

        return "create/index";
    }

    @PostMapping("/create")
    public String submitForm(@ModelAttribute Event event) {
        event.setImages(new ArrayList<>());
        event.setOwnerID(1);
        service.addEvent(event);

        return "create/index";
    }
}
