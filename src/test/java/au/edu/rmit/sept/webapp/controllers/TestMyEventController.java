package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepositoryImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestMyEventController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAccountWithLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new User("test", "user", "login", "email@email.com", User.permissions.STUDENT));
        mockMvc.perform(get("/account/myEvents").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("account/myEvents/index"));
    }

    @Test
    void testAccountNoLogin() throws Exception {
        mockMvc.perform(get("/account/myEvents"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
