package au.edu.rmit.sept.webapp.controllers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.UserInterestsService;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.entity.Event;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class EventFeedControllerTest {

    @DynamicPropertySource
    static void overrideUploadsRoot(DynamicPropertyRegistry r) throws Exception {
        java.nio.file.Path tmp = java.nio.file.Files.createTempDirectory("evt-hub-test-uploads");
        r.add("app.uploads.root", () -> tmp.toString());
    }
    
    private final static String URL = "/events";

    private User admin() {
        User u = new User("Bingus", "Bongus", "s3cur3pass", "bingus@example.com", User.permissions.ADMIN);
        u.setId(999);
        return u;
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private UserInterestsService userInterestService;

    @Test
    void shouldDisplayEventFeedContent() throws Exception {
        when(eventService.getEventsByFilter(null, null, null, null, null)).thenReturn(List.of());
        when(eventService.getTags()).thenReturn(List.of("education", "beginner"));
        when(eventService.getKeywords()).thenReturn(List.of("java", "workshop"));

        mvc.perform(get(URL))
            .andExpect(status().isOk())
            .andExpect(view().name("events/index"))
            .andExpect(model().attributeExists("events"))
            .andExpect(model().attributeExists("tags"))
            .andExpect(model().attributeExists("keywords"));
    }
    
    @Test
    void checkOwnerIsOwner() throws Exception {
        User organiser = new User("Bitsy", "Mouse", "cheese", "bitsy@example.com", User.permissions.ORGANISER);
        organiser.setId(4);

        when(eventService.getOwnerID(2)).thenReturn(4);

        mvc.perform(get("/events/modal/2").sessionAttr("user", organiser))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isOwner").value(true));
    }

    @Test
    void checkOwnerWrongUserID() throws Exception {
        User organiser = new User("Bitsy", "Mouse", "cheese", "bitsy@example.com", User.permissions.ORGANISER);
        organiser.setId(10);

        when(eventService.getOwnerID(2)).thenReturn(4);

        mvc.perform(get("/events/modal/2").sessionAttr("user", organiser))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isOwner").value(false));
    }

    @Test
    void checkOwnerWrongUserPermissions() throws Exception {
        User student = new User("Bitsy", "Mouse", "cheese", "bitsy@example.com", User.permissions.STUDENT);
        student.setId(4);

        when(eventService.getOwnerID(2)).thenReturn(4);

        mvc.perform(get("/events/modal/2").sessionAttr("user", student))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isOwner").value(false));
    }

    @Test
    void eventDetailsModalExists() throws Exception {
        Event event = new Event();
        event.setId(1);

        when(eventService.findEventbyID(1)).thenReturn(event);

        mvc.perform(post("/events").param("id", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("events/index"))
            .andExpect(model().attributeExists("modal-content"));
    }

    @Test
    void deleteImageSuccess() throws Exception {
        when(eventService.deleteImageForEvent(1, 0)).thenReturn(true);

        mvc.perform(delete("/events/1/images/0")
                .sessionAttr("user", admin()))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteImageFail() throws Exception {
        when(eventService.deleteImageForEvent(1, 0)).thenReturn(false);

        mvc.perform(delete("/events/1/images/0")
                .sessionAttr("user", admin()))
            .andExpect(status().isNotFound());
    }

    @Test
    void addImageSuccess() throws Exception {
        when(eventService.addImageForEvent(eq(1), anyString())).thenReturn(true);

        var file = new org.springframework.mock.web.MockMultipartFile(
                "file", "pic.jpg", "image/jpeg", new byte[]{1,2,3});

        mvc.perform(multipart("/events/{id}/images", 1)
                .file(file)
                .sessionAttr("user", admin()))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", startsWith("/uploads/events/1/")))
        .andExpect(jsonPath("$.url", startsWith("/uploads/events/1/")))
        .andExpect(jsonPath("$.filename").exists());
    }

    @Test
    void addImageBadRequest_NoFile() throws Exception {
        mvc.perform(multipart("/events/{id}/images", 1)
                .sessionAttr("user", admin()))
        .andExpect(status().isBadRequest());
    }

    @Test
    void addImageBadRequest_NotImage() throws Exception {
        var file = new org.springframework.mock.web.MockMultipartFile(
                "file", "notes.txt", "text/plain", "hello".getBytes());

        mvc.perform(multipart("/events/{id}/images", 1)
                .file(file)
                .sessionAttr("user", admin()))
        .andExpect(status().isBadRequest());
    }

    @Test
    void addImageForbidden_NoUser() throws Exception {
        var file = new org.springframework.mock.web.MockMultipartFile(
                "file", "pic.jpg", "image/jpeg", new byte[]{1});

        mvc.perform(multipart("/events/{id}/images", 1).file(file))
        .andExpect(status().isForbidden());
    }

    @Test
    void addImageNotFound_EventMissing() throws Exception {
        when(eventService.addImageForEvent(eq(1), anyString())).thenReturn(false);

        var file = new org.springframework.mock.web.MockMultipartFile(
                "file", "pic.jpg", "image/jpeg", new byte[]{1});

        mvc.perform(multipart("/events/{id}/images", 1)
                .file(file)
                .sessionAttr("user", admin()))
        .andExpect(status().isNotFound());
    }

    @Test
    void deleteEventSuccess() throws Exception {
        when(eventService.deleteEventbyID(1)).thenReturn(true);

        mvc.perform(delete("/events/1")
                .sessionAttr("user", admin()))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteEventFail() throws Exception {
        when(eventService.deleteEventbyID(1)).thenReturn(false);

        mvc.perform(delete("/events/1")
                .sessionAttr("user", admin()))
            .andExpect(status().isNotFound());
    }

    @Test
    void recommendedEventsRender() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.ORGANISER);

        MvcResult result = mvc.perform(get(URL)
            .sessionAttr("user", testUser))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attribute("user", testUser))
            .andExpect(status().isOk())
            .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Recommended Events"));
        assertTrue(pageContent.contains("No events currently match your selected tags or categories."));
        
    }

    @Test
    void checkRecommendedEventsAreDisplayed() throws Exception {
        User testUser = new User(1, "Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT, false);

        Event event1 = new Event(1, "Losing Fights", "Get beat up by omniman", 1, LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0), "Earth", "90", "GENERAL", List.of(""), List.of("ADULT", "ACCESSIBLE"), List.of(""));

        Event event2 = new Event(2, "Writing Tests", "The worst torture method available", 1, LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0), "Earth", "90", "GENERAL", List.of(""), List.of("ADULT", "ACCESSIBLE"), List.of(""));
        List<Event> events = Arrays.asList(event1, event2);

        HashSet<String> interests = new HashSet<>(Set.of("GENERAL", "ACCESSIBLE"));

        Mockito.when(userInterestService.getUserInterests(1)).thenReturn(interests);
        Mockito.when(eventService.getEventsByFilter(null, null, null, null, null)).thenReturn(events);

        MvcResult result = mvc.perform(get(URL)
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Losing Fights"));
        assertTrue(pageContent.contains("Writing Tests"));






    }



}