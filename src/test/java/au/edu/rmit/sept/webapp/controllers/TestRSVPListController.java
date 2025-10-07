package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.EventParticipantsRepository;
import au.edu.rmit.sept.webapp.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRSVPListController {

    private static final String URL = "/account/rsvpList";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventParticipantsRepository eventParticipantsRepository;

    @MockBean
    private EventRepository eventRepository;


    @Test
    void checkRSVPListIsRetrieved() throws Exception {
        User testUser = new User(1, "Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT, false);

        List<Integer> mockEventIds = Arrays.asList(1, 2);
        Event event1 = new Event();
        Event event2 = new Event();

        Mockito.when(eventParticipantsRepository.getRSVPList(1)).thenReturn(mockEventIds);
        Mockito.when(eventRepository.findEventbyID(1)).thenReturn(event1);
        Mockito.when(eventRepository.findEventbyID(2)).thenReturn(event2);

        mockMvc.perform(get(URL)
                        .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andExpect(model().attributeExists("rsvpList"))
                .andExpect(model().attribute("rsvpList", List.of(event1, event2)));





    }

}
