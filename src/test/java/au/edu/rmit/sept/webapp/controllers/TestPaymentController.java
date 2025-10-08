// package au.edu.rmit.sept.webapp.controllers;

// import au.edu.rmit.sept.webapp.entity.*;
// import au.edu.rmit.sept.webapp.services.*;
// import au.edu.rmit.sept.webapp.models.PaymentRequest;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.mock.web.MockHttpSession;

// import java.time.LocalDateTime;
// import java.time.Month;
// import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class TestPaymentController {
//     private User user = new User(1, "TestFirst", "TestLast", "TestPass", "TestEmail@example.com", User.permissions.STUDENT, false);
//     private Event event = new Event(1, "Test", "TestDesc", 1, LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0), "TestLoc", "10.00", "TestCat", List.of("Test"), List.of("Test"), List.of("Test"));
//     private PaymentRequest paymentRequest = new PaymentRequest("TestFirst", "TestEmail@example.com", 100.0, "4242424242424242", 12, 2099, "123");

//     @Autowired
//     private MockMvc mockMvc;
//     @MockBean
//     private EventService eventService;
//     @MockBean
//     private UserService userService;

//     @MockBean
//     private PaymentService paymentService;
//     @MockBean
//     private EventParticipantsService eventParticipantsService;

//     private MockHttpSession session;

//     @BeforeEach
//     void setUp() throws Exception{
//         when(eventService.getEvent(1, "Test")).thenReturn(event);
//         when(userService.findUserByEmail("TestEmail@example.com")).thenReturn(user);
//         when(userService.findUserByID(1)).thenReturn(user);

//         session = new MockHttpSession();
//         session.setAttribute("user", user);
//     }

//     @Test
//     void testCheckout() throws Exception {
//         mockMvc.perform(get("/checkout").param("id", "1").param("title", "Test").session(session))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("payment/checkout"))
//                 .andExpect(model().attributeExists("payment", "years", "userId", "event", "date"));
//     }

//     @Test
//     void testCheckoutNoLogin() throws Exception {
//         mockMvc.perform(get("/checkout").param("id", "1").param("title", "Test"))
//                 .andExpect(status().is3xxRedirection())
//                 .andExpect(redirectedUrl("/login"));
//     }

//     @Test
//     void testPay() throws Exception {
//         mockMvc.perform(post("/pay")
//                 .param("eventId", "1")
//                 .param("eventTitle", "Test")
//                 .param("userId", "1")
//                 .flashAttr("payment", paymentRequest).session(session))
//                 .andExpect(status().is3xxRedirection())
//                 .andExpect(redirectedUrlPattern("/receipt?id=1&title=Test&ref=*&userId=1"));
//     }

//     @Test
//     void testPayError() throws Exception {
//         PaymentRequest invalidPayment = new PaymentRequest();
//         invalidPayment.setEmail("NotEmail");
//         mockMvc.perform(post("/pay")
//                         .param("eventId", "1")
//                         .param("eventTitle", "Test")
//                         .param("userId", "1")
//                         .flashAttr("payment", invalidPayment).session(session))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("payment/checkout"))
//                 .andExpect(model().attributeHasFieldErrors("payment","email"));
//     }

//     @Test
//     void testReceipt() throws Exception {
//         String ref = "REF-12345678";

//         mockMvc.perform(get("/receipt")
//                         .param("id", "1")
//                         .param("title", "Test")
//                         .param("ref", ref)
//                         .param("userId", "1"))
//                 .andExpect(status().isOk())
//                 .andExpect(model().attribute("event", event))
//                 .andExpect(model().attribute("user", user))
//                 .andExpect(model().attribute("ref", ref))
//                 .andExpect(model().attributeExists("time"));

        verify(paymentService).AddPayment(any(Payment.class));
        verify(eventParticipantsService).addEventParticipant(event.getId(), user.getId());
    }
}
