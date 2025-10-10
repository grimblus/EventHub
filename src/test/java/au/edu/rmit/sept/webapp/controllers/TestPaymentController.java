package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import au.edu.rmit.sept.webapp.services.PaymentService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPaymentController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired 
    PaymentService paymentService;

    String boba = "sk_test_51SDcW856QKuDJ2iodsPwBTom6zY9f6b3";
    String matcha = "1MPUykXFsnhLqP9iMHM76xFVJPSkSZbLDV39er3I1Gkaow9yMQin4ptL00Z8rQ76Et";

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
        mockMvc.perform(get("/receipt?id=1&title=Workshop&ref=REF-xxxxxxxx&userId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("receipt/receipt"));
    }

    @Test
    void TestRecieptBadData() throws Exception{
        mockMvc.perform(get("/receipt?id=xx&title=xx&ref=pdpoops-xxxxxxxx&userId=xx"))
                .andExpect(status().is4xxClientError());
    }
    


}