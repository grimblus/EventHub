package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    private static final String URL = "/";

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldDisplayHomeContent() throws Exception {
        MvcResult result = mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertAll(
                ()-> assertTrue(pageContent.contains("Welcome to EventHub")),
                ()-> assertTrue(pageContent.contains("Welcome to EventHub - your new all-in-one campus event management system!" )),
                ()-> assertTrue(pageContent.contains("Sign up or log-in now!"))
        );


    }
}
