package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.repository.UserRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    private final static String URL = "/login";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void shouldDisplayLoginContent() throws Exception {
        MvcResult result = mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertAll(
                ()-> assertTrue(pageContent.contains("Email:")),
                ()-> assertTrue(pageContent.contains("Password:"))
        );
    }

    @Test
    void invalidEmailWrong() throws Exception {
        MvcResult result = mvc.perform(post(URL)
                .param("email", "nonexistent@email.com")
                .param("pw", "anyPassword"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("No account found with that email."));
    }

    @Test
    void invalidEmailEmpty() throws Exception {
        MvcResult result = mvc.perform(post(URL)
                .param("email", "")
                .param("pw", "anyPassword"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("No account found with that email."));
    }

    @Test
    void invalidPassword() throws Exception {
        mvc.perform(post("/login")
                .param("email", "aaron@example.com")
                .param("pw", "bingus"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Invalid password."));
    }

    @Test
    void invalidPasswordEmpty() throws Exception {
        mvc.perform(post("/login")
                .param("email", "aaron@example.com")
                .param("pw", ""))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Invalid password."));
    }
    
    @Test
    void invalidEmailandPasswordEmpty() throws Exception {
        MvcResult result = mvc.perform(post(URL)
                .param("email", "")
                .param("pw", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("No account found with that email."));
    }
}