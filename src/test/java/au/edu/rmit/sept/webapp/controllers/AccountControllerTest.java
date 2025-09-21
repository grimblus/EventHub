package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    private static final String URL = "/account";

    @Autowired
    private MockMvc mvc;

    @Test
    void accountDisplaysName() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT);

        MvcResult result = mvc.perform(get(URL)
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Hello, Mark Grayson"));
    }

    @Test
    void accountRedirectsIfNotLoggedIn() throws Exception {

        mvc.perform(get(URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

}
