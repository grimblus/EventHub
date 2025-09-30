package au.edu.rmit.sept.webapp.templates.create;

import au.edu.rmit.sept.webapp.entity.User;
import org.junit.jupiter.api.BeforeAll;
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
    private static User mockUser;

    @BeforeAll
    static void setup() {
        mockUser = new User();
        mockUser.setPermissionLevel(User.permissions.ORGANISER);
    }

    @Autowired
    private MockMvc mockMvc;

    // test if create events page renders when logged in as ORGANISER
    @Test
    @WithMockUser(username = "johndev@example.com", roles = {"ORGANISER"})
    void createPage_renders() throws Exception {
        mockMvc.perform(get("/create").sessionAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Event Hub - Create")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Title:")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Description:")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Location:")));
    }
}
