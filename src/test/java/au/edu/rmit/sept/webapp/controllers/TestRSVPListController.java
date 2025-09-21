package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRSVPListController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAccount() throws Exception {
        User user = new User();
        Event event1 = new Event();
        Event event2 = new Event();
        ArrayList<Event> events = new ArrayList<Event>() {{
            add(event1);
            add(event2);
        }};
        user.setRsvpList(events);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        mockMvc.perform(get("/account/rsvpList").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("account/rsvpList/index"))
                .andExpect(model().attributeExists("rsvpList"))
                .andExpect(model().attribute("rsvpList", List.of(event1, event2)));
    }

    @Test
    void testAccountUserNull() throws Exception {
        mockMvc.perform(get("/account/rsvpList"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/rsvpList/index"))
                .andExpect(model().attribute("rsvpList", List.of()));
    }
}
