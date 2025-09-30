package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAdminController {
    User user = new User();
    Event event = new Event();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private EventService eventService;
    private AdminController controller = new AdminController(userService, eventService);

    @Test
    void testIsAdminSuccess() throws Exception {
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setPermissionLevel(User.permissions.ADMIN);

        when(session.getAttribute("user")).thenReturn(user);

        assertTrue(controller.isAdmin(session));
    }

    @Test
    void testIsAdminFailure() throws Exception {
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setPermissionLevel(User.permissions.STUDENT);

        when(session.getAttribute("user")).thenReturn(user);

        assertFalse(controller.isAdmin(session));
    }

    @Test
    void testIsAdminNull() throws Exception {
        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute("user")).thenReturn(null);

        assertFalse(controller.isAdmin(session));
    }

    @Test
    void testShowUsers() throws Exception {
        when(userService.getUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"))
                .andExpect(model().attribute("active", "users"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getUsers();
    }

    @Test
    void testShowEvents() throws Exception {
        when(eventService.getEvents()).thenReturn(List.of(event));

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"))
                .andExpect(model().attribute("active", "events"))
                .andExpect(model().attributeExists("events"));

        verify(eventService).getEvents();
    }

    @Test
    void testToggleBanned() throws Exception  {
        mockMvc.perform(post("/admin/toggleBanned/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService).toggleBanned(1);
    }

    @Test
    void testDeleteEvent() throws Exception  {
        mockMvc.perform(post("/admin/deleteEvent/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));

        verify(eventService).deleteEventbyID(1);
    }
}
