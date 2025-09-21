package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.*;
import au.edu.rmit.sept.webapp.repository.*;
import au.edu.rmit.sept.webapp.services.*;
import au.edu.rmit.sept.webapp.models.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPaymentController {
    private User user = new User("TestFirst", "TestLast", "TestPass", "TestEmail", User.permissions.STUDENT);
    private Event event = new Event("Test", "TestDesc", 1, LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0), "TestLoc", "10.00", "TestCat", List.of("Test"), List.of("Test"), List.of("Test"));

    @Autowired
    private MockMvc mockMvc;

    private SandboxPaymentGateway gateway;
    @MockBean
    private EventService eventService;
    @MockBean
    private UserService userService;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private EventParticipantsService eventParticipantsService;

    private MockHttpSession session (User user) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        return session;
    }

    @Test
    void testCheckout() throws Exception {
        when(eventService.getEvent(1, "Test")).thenReturn(event);
        when(userService.findUserByEmail("TestEmail")).thenReturn(user);

        mockMvc.perform(get("/checkout").param("id", "1").param("title", "Test").session(session(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/checkout"))
                .andExpect(model().attributeExists("payment", "years", "userId", "event", "date"));
    }

    @Test
    void testCheckoutNoLogin() throws Exception {
        mockMvc.perform(get("/checkout").param("id", "1").param("title", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testPay() throws Exception {

    }

    @Test
    void testReceipt() throws Exception {

    }
}
