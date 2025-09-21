package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestMyNotificationsController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAccount() throws Exception {
        mockMvc.perform(get("/account/myNotifications"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/myNotifications/index"));
    }
}
