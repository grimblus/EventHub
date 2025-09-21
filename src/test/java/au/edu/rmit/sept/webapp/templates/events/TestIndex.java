package au.edu.rmit.sept.webapp.templates.events;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestIndex {

    @Autowired
    private MockMvc mockMvc;

    // simple test looking for title, searchbar
    @Test
    void events_renders() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/index"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("EventHub - Events")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("form")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("search")));
    }
}