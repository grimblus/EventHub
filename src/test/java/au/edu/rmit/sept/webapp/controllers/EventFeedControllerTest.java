package au.edu.rmit.sept.webapp.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.entity.Event;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class EventFeedControllerTest {
    
    private final static String URL = "/events";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

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

        mvc.perform(delete("/events/1/images/0"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteImageFail() throws Exception {
        when(eventService.deleteImageForEvent(1, 0)).thenReturn(false);

        mvc.perform(delete("/events/1/images/0"))
            .andExpect(status().isNotFound());
    }

    @Test
    void addImageSuccess() throws Exception {
        when(eventService.addImageForEvent(1, "temp.png")).thenReturn(true);

        mvc.perform(post("/events/1/images"))
            .andExpect(status().isNoContent());
    }

    @Test
    void addImageFail() throws Exception {
        when(eventService.addImageForEvent(1, "temp.png")).thenReturn(false);

        mvc.perform(post("/events/1/images"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteEventSuccess() throws Exception {
        when(eventService.deleteEventbyID(1)).thenReturn(true);

        mvc.perform(delete("/events/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteEventFail() throws Exception {
        when(eventService.deleteEventbyID(1)).thenReturn(false);

        mvc.perform(delete("/events/1"))
            .andExpect(status().isNotFound());
    }
}