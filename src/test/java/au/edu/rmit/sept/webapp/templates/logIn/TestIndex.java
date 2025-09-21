package au.edu.rmit.sept.webapp.templates.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestIndex {

    @Autowired
    private MockMvc mockMvc;

    // simple render test for log in form
    @Test
    void login_page_renders() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Welcome to EventHub</title>")))
                .andExpect(content().string(containsString("<h1>Log In to EventHub</h1>")))
                // Thymeleaf has rendered th:action="@{/login}" into action="/login"
                .andExpect(content().string(containsString("<form action=\"/login\" method=\"post\">")))
                .andExpect(content().string(containsString("id=\"email\"")))
                .andExpect(content().string(containsString("id=\"pw\"")))
                .andExpect(content().string(containsString("type=\"submit\"")))
                .andExpect(content().string(containsString("href=\"/signUp\""))); // “Register here” link
    }
}
