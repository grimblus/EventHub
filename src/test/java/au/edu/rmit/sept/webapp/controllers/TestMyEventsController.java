package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepositoryImpl;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestMyEventsController {
    private final static String URL = "/account/myEvents";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testMyEventsWithLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new User("test", "user", "login", "email@email.com", User.permissions.STUDENT));
        MvcResult result = mockMvc.perform(get(URL).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("account/myEvents/index"))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        
        assertTrue(pageContent.contains("Your saved events"));
        assertTrue(pageContent.contains("You have not saved any events."));



    }

    @Test
    void testAccountNoLogin() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void savedEventsAreRetrieved()  throws Exception {
         User testUser = new User(1, "Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT, false);
        Event event1 = new Event(1, "Losing Fights", "Get beat up by omniman", 1, LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0), "Earth", "90", "GENERAL", List.of(""), List.of("ADULT", "ACCESSIBLE"), List.of(""));

        Event event2 = new Event(2, "Writing Tests", "The worst torture method available", 1, LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0), "Earth", "90", "FUN", List.of(""), List.of("ADULT"), List.of(""));
      
        List<Event> mockEvents = Arrays.asList(event1, event2);
        

        Mockito.when(userService.getSavedEvents(1)).thenReturn(mockEvents);
       

        MvcResult result = mockMvc.perform(get(URL)
                        .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andExpect(model().attributeExists("savedEvents"))
                .andExpect(model().attribute("savedEvents", List.of(event1, event2)))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Losing Fights"));
        assertTrue(pageContent.contains("Writing Tests"));


    }
}
