package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCreateController {
    private static final String URL = "/create";
    private static User mockUser;

    @BeforeAll
    public static void setup() {
        mockUser = new User();
        mockUser.setId(1);
        mockUser.setPermissionLevel(User.permissions.ORGANISER);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateDisplayFormContent() throws Exception {
        MvcResult result = mockMvc.perform(get(URL).sessionAttr("user", mockUser))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertAll(
                ()-> assertTrue(pageContent.contains("Title:")),
                ()-> assertTrue(pageContent.contains("Description:" )),
                ()-> assertTrue(pageContent.contains("Date:")),
                ()-> assertTrue(pageContent.contains("Location:")),
                ()-> assertTrue(pageContent.contains("Ticket Price:")),
                ()-> assertTrue(pageContent.contains("Category:")),
                ()-> assertTrue(pageContent.contains("Keywords:")),
                ()-> assertTrue(pageContent.contains("Tags:"))
        );
    }

    @Test
    public void testEventModalAttributeInitialised() throws Exception {
        MvcResult result = mockMvc.perform(get(URL).sessionAttr("user", mockUser))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = result.getModelAndView();
        assertNotNull(mav);
        Event event = (Event) mav.getModel().get("event");
        assertAll(
                () -> assertNotNull(event),
                () -> assertEquals("", event.getTitle()),
                () -> assertEquals("", event.getDescription()),
                () -> assertEquals(LocalDateTime.MIN, event.getDate()),
                () -> assertEquals("", event.getLocation()),
                () -> assertEquals("", event.getTicketPrice()),
                () -> assertEquals("", event.getCategory()),
                () -> assertEquals(new ArrayList<>(), event.getKeywords()),
                () -> assertEquals(new ArrayList<>(), event.getTags())
        );
    }

    @Test
    public void testValidFormInput() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                .param("title", "Cool Event")
                .param("description", "")
                .param("date", LocalDateTime.now().plusDays(2).toString())
                .param("location", "Gurt: Yo")
                .param("ticketPrice", "10")
                .param("category", "")
                .param("keywords", "")
                .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Successfully Added Event!"));
    }

    @Test
    public void testNoTitle() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                .param("title", "")
                .param("description", "")
                .param("date", LocalDateTime.now().plusDays(2).toString())
                .param("location", "Gurt: Yo")
                .param("ticketPrice", "10")
                .param("category", "")
                .param("keywords", "")
                .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Title cannot be empty!"));
    }

    @Test
    public void testNoDate() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Cool Event")
                        .param("description", "")
                        .param("date", "")
                        .param("location", "Gurt: Yo")
                        .param("ticketPrice", "10")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("Date cannot be empty!"));
    }

    @Test
    public void testNoLocation() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Cool Event")
                        .param("description", "")
                        .param("date", LocalDateTime.now().plusDays(2).toString())
                        .param("location", "")
                        .param("ticketPrice", "10")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("Location cannot be empty!"));
    }

    @Test
    public void testInvalidShortTitleLength() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Yo")
                        .param("description", "")
                        .param("date", LocalDateTime.now().plusDays(2).toString())
                        .param("location", "Gurt: Yo")
                        .param("ticketPrice", "10")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("Title length must be between 3 and 25 characters!"));
    }

    @Test
    public void testInvalidLongTitleLength() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Yo gurt, gurt: yo, yo: gur")
                        .param("description", "")
                        .param("date", LocalDateTime.now().plusDays(2).toString())
                        .param("location", "Gurt: Yo")
                        .param("ticketPrice", "10")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();
        assertTrue(pageContent.contains("Title length must be between 3 and 25 characters!"));
    }

    @Test
    public void testInvalidDescription() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Cool Event")
                        .param("description", "This is a specially generated string designed to be exactly three hundred and one characters in total length, including every single space, letter, and punctuation mark, so that you can use it for testing, validation, or any other purpose that requires such precision today. gurtyogurtyogurtyogurtyogu")
                        .param("date", LocalDateTime.now().plusDays(2).toString())
                        .param("location", "Gurt: Yo")
                        .param("ticketPrice", "10")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Description length must be less than 300 characters!"));
    }

    @Test
    public void testInvalidDate() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Cool Event")
                        .param("description", "")
                        .param("date", LocalDateTime.now().minusSeconds(1).toString())
                        .param("location", "Gurt: Yo")
                        .param("ticketPrice", "10")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Date cannot be today or before today!"));
    }

    @Test
    public void testInvalidTicketPrice() throws Exception {
        MvcResult result = mockMvc.perform(post(URL).sessionAttr("user", mockUser)
                        .param("title", "Cool Event")
                        .param("description", "")
                        .param("date", LocalDateTime.now().plusDays(2).toString())
                        .param("location", "Gurt: Yo")
                        .param("ticketPrice", "-1")
                        .param("category", "")
                        .param("keywords", "")
                        .param("tags", ""))
                .andExpect(status().isOk())
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Ticket price cannot be negative!"));
    }
}
