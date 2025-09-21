package au.edu.rmit.sept.webapp.templates.signUp;

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
class IndexTest {

    @Autowired
    private MockMvc mockMvc;

    // test that signup renders form with required inputs
    @Test
    void signup_page_renders() throws Exception {
        mockMvc.perform(get("/signUp"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Welcome to EventHub</title>")))
                .andExpect(content().string(containsString("<h1>Sign Up To EventHub</h1>")))
                .andExpect(content().string(containsString("<form action=\"/signUp\" method=\"post\">")))
                .andExpect(content().string(containsString("id=\"firstname\"")))
                .andExpect(content().string(containsString("id=\"lastname\"")))
                .andExpect(content().string(containsString("id=\"email\"")))
                .andExpect(content().string(containsString("id=\"password\"")));
    }
}
