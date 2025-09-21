package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

    private final static String URL = "/signUp";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldDisplaySignUpContent() throws Exception {
        MvcResult result = mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertAll(
                ()-> assertTrue(pageContent.contains("First Name:")),
                ()-> assertTrue(pageContent.contains("Last Name:" )),
                ()-> assertTrue(pageContent.contains("Email:")),
                ()-> assertTrue(pageContent.contains("Password:")),
                ()-> assertTrue(pageContent.contains("Sign up as a:"))
        );
    }

    @Test
    void userModelAttributeInitialised() throws Exception {
        MvcResult result = mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = result.getModelAndView();
        assertNotNull(mav);
        User user = (User) mav.getModel().get("user");
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("", user.getFirstName()),
                () -> assertEquals("", user.getLastName()),
                () -> assertEquals("", user.getEmail()),
                () -> assertEquals("", user.getPassword()),
                () -> assertEquals(User.permissions.STUDENT, user.getPermissionLevel()),
                () -> assertEquals(0, user.getRsvpList().size()),
                () -> assertEquals(0, user.getId())
        );

    }

    @Test
    void validSignUp() throws Exception {

        mvc.perform(post("/signUp")
                .param("firstName", "Nolan")
                .param("lastName", "Grayson")
                .param("password", "Invincible53@")
                .param("email", "omniman@email.com")
                .param("permissionLevel", "STUDENT"))
                .andExpect(status().is3xxRedirection());



    }


    @Test
    void invalidFirstNameNumbers() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "34")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                        .andExpect(status().isOk())
                        .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("First name must not contain numbers"));

    }

    @Test
    void invalidFirstNameSymbols() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "&*")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("First name must not contain symbols or spaces"));

    }


    @Test
    void invalidFirstNameSpaces() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "    ")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("First name must not contain symbols or spaces"));

    }

    @Test
    void invalidFirstNameLength() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "A")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("First name must be at least 2 characters"));

    }

    @Test
    void invalidLastNameNumbers() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "34")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Last name must not contain numbers"));

    }

    @Test
    void invalidLastNameSymbols() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "*#")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Last name must not contain symbols or spaces"));

    }

    @Test
    void invalidLastNameSpaces() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "    ")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Last name must not contain symbols or spaces"));

    }

    @Test
    void invalidLastNameLength() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "G")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Last name must be at least 2 characters"));

    }

    @Test
    void invalidEmailEmptyStart() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidEmailSymbolStart() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "*@&(@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidEmailNoAtSign() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omnimanemail.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidEmailNoMiddle() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidEmailSymbolMiddle() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@*@&#(.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidEmailNoPeriod() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@emailcom")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidEmailNoCom() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "Grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Email must follow the format: x@x.com"));

    }

    @Test
    void invalidPasswordLength() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "G")
                        .param("password", "@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Password must be at least 8 characters"));

    }

    @Test
    void passwordNoSymbols() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "G")
                        .param("password", "Invincible53")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Password must contain at least 1 symbol, number and uppercase letter"));

    }

    @Test
    void passwordNoNumbers() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "G")
                        .param("password", "Invincible@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Password must contain at least 1 symbol, number and uppercase letter"));

    }

    @Test
    void passwordNoUppercaseLetter() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "Nolan")
                        .param("lastName", "G")
                        .param("password", "invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Password must contain at least 1 symbol, number and uppercase letter"));

    }


    @Test
    void inputSanitisation() throws Exception {

        MvcResult result = mvc.perform(post("/signUp")
                        .param("firstName", "nolan")
                        .param("lastName", "grayson")
                        .param("password", "Invincible53@")
                        .param("email", "omniman@email.com")
                        .param("permissionLevel", "STUDENT"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        HttpSession session = result.getRequest().getSession(false);
        assertNotNull(session);

        User user = (User)  session.getAttribute("user");
        assertAll(
                //checks it is capitalised
                () -> assertEquals("Nolan", user.getFirstName()),
                () -> assertEquals("Grayson", user.getLastName())

        );





    }
}