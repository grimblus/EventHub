package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.entity.Event.CATEGORIES;
import au.edu.rmit.sept.webapp.entity.Event.KEYWORDS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTest {
     private static final String URL = "/dashboard";

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private EventService eventService;

    @MockBean
    private EventParticipantsService eventParticipantsService;

    @Test
    void dashboardRenders() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER);

        MvcResult result = mvc.perform(get(URL)
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Your created events"));
        assertTrue(pageContent.contains("You have not created any events."));

    }

    @Test
    void dashboardRedirectsIfNotLoggedIn() throws Exception {

        mvc.perform(get(URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

    @Test
    void displaysCreatedEvents() throws Exception {
        User testUser = new User(1, "Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER, false);
        Event event1 = new Event(1, "Bike Ride", "Go bike riding with the friends you don't have!", 1, null, "Hell", 
        "800", "General", List.of(KEYWORDS.CHALLENGE.toString()) ,List.of(CATEGORIES.CAREER.toString()), List.of());

        when(eventService.getEventsByOwnerId(1)).thenReturn(List.of(event1));

        MvcResult result = mvc.perform(get(URL)
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Bike Ride"));
        assertTrue(pageContent.contains("Hell"));

    }



    @Test
    void eventRsvpListRenders() throws Exception {
        User testUser = new User(1, "Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER, false);
        

        MvcResult result = mvc.perform(get(URL + "/1")
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Event RSVP List"));
        assertTrue(pageContent.contains("No users have RSVP'd to this event."));
    }

    @Test
    void eventRSVPListRedirectsIfNotLoggedIn() throws Exception {

        mvc.perform(get(URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

    @Test
    void eventRsvpListRendersWithRSVPs() throws Exception {
        User testUser = new User(1, "Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER, false);
        User attendee = new User(1, "Conan", "OBrien", "ImWearingAWig33@", "coco@email.com", User.permissions.ORGANISER, false);

        when(eventParticipantsService.getEventParticipants(1)).thenReturn(List.of(attendee));

        MvcResult result = mvc.perform(get(URL + "/1")
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Conan"));
        assertTrue(pageContent.contains("OBrien"));
        assertTrue(pageContent.contains("coco@email.com"));

    }



}
