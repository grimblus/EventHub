package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.entity.User.permissions;
import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import au.edu.rmit.sept.webapp.services.PaymentService;
import au.edu.rmit.sept.webapp.services.UserService;

import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPaymentController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired 
    PaymentService paymentService;

    String boba = "sk_test_51SDcW856QKuDJ2iodsPwBTom6zY9f6b3";
    String matcha = "1MPUykXFsnhLqP9iMHM76xFVJPSkSZbLDV39er3I1Gkaow9yMQin4ptL00Z8rQ76Et";
    
    private MockHttpSession session;
    private Event testEvent;
    private User testUser;

    @MockBean
    private EventService eventService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        // need to have logged in user to buy ticket
        session = new MockHttpSession();
        testUser = new User();
        testUser.setId(99);
        testUser.setFirstName("Test");
        testUser.setLastName("user");
        testUser.setPassword("12345");
        testUser.setEmail("test@gmail.com");

        session.setAttribute("user", testUser);

        testEvent = new Event();
        testEvent.setId(99);
        testEvent.setTitle("Test Event");
        testEvent.setTicketPrice("50");
        testEvent.setDate(LocalDateTime.now());
        testEvent.setLocation("Test Location");
        testEvent.setDescription("Test Description");
    }

    @Test 
    void testCreatePaymentIntent() throws Exception {
        Stripe.apiKey = boba + matcha;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(1000L)
            .setCurrency("aud").
            setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .build();

        assertNotNull(PaymentIntent.create(params));
    }

    @Test 
    void testReciept() throws Exception{

        // when we call these
        when(eventService.getEvent(testEvent.getId(), testEvent.getTitle())).thenReturn(testEvent);
        when(userService.findUserByID(testUser.getId())).thenReturn(testUser);

        mockMvc.perform(get("/receipt")
                .param("id", String.valueOf(testEvent.getId()))
                .param("title", testEvent.getTitle())
                .param("ref", "REF-TEST")
                .param("userId", String.valueOf(testUser.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("receipt/receipt"));
    }

    @Test
    void TestRecieptBadData() throws Exception{
        mockMvc.perform(get("/receipt?id=xx&title=xx&ref=pdpoops-xxxxxxxx&userId=xx"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    void printReceiptReturnsPdf() throws Exception {
        // Mock the event service to return a test event
        when(eventService.getEvent(anyInt(), anyString())).thenReturn(testEvent);

        mockMvc.perform(post("/print")
                .param("eventId", String.valueOf(testEvent.getId()))
                .param("eventTitle", testEvent.getTitle())
                .param("bought", "11/10/2025 @ 12:00")
                .param("refVal", "REF-TEST"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=receipt-REF-TEST.pdf"))
                .andExpect(content().contentType("application/pdf"));
    }

}