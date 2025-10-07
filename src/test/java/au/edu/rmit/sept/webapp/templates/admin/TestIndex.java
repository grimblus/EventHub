package au.edu.rmit.sept.webapp.templates.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestIndex {

    @Autowired
    private MockMvc mockMvc;

    // When logged in as ADMIN, the page should render
    @Test
    @WithMockUser(username = "bingus@example.com", roles = {"ADMIN"})
    void adminPage_renders() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }
}