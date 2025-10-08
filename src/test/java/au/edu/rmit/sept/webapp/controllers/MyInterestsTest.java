package au.edu.rmit.sept.webapp.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;

@SpringBootTest
@AutoConfigureMockMvc
public class MyInterestsTest {
     private static final String URL = "/account/myInterests";

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private EventService eventService;

    @MockBean
    private EventParticipantsService eventParticipantsService;

    @Test
    void interestsRender() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER);

        MvcResult result = mvc.perform(get(URL)
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Your Interests"));
        assertTrue(pageContent.contains("GENERAL"));
        assertTrue(pageContent.contains("CODING"));
        assertTrue(pageContent.contains("PARTY"));
        assertTrue(pageContent.contains("ACCESSIBLE"));
        assertTrue(pageContent.contains("ADULT"));

    }

    

    
}
