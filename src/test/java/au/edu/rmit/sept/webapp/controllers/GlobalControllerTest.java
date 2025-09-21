package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalControllerTest {

    private static final String URL = "/";

    @Autowired
    private MockMvc mvc;

    @Test
    void checkLoggedIn() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT);

        mvc.perform(get(URL)
                        .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loggedIn"))
                .andExpect(model().attribute("loggedIn", true));
    }

    @Test
    void checkNotLoggedIn() throws Exception {

        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loggedIn"))
                .andExpect(model().attribute("loggedIn", false));
    }

    @Test
    void checkIsOrganiserTrue() throws Exception{
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER);

        mvc.perform(get(URL)
                        .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("isOrganiser"))
                .andExpect(model().attribute("isOrganiser", true));
    }

    @Test
    void checkIsOrganiserFalse() throws Exception{
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT);

        mvc.perform(get(URL)
                        .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("isOrganiser"))
                .andExpect(model().attribute("isOrganiser", false));
    }

    @Test
    void checkIsOrganiserLoggedOut() throws Exception{

        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("isOrganiser"))
                .andExpect(model().attribute("isOrganiser", false));
    }
}
