package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPaymentController {

    @Autowired
    private MockMvc mockMvc;


    String boba = "sk_test_51SDcW856QKuDJ2iodsPwBTom6zY9f6b3";
    String matcha = "1MPUykXFsnhLqP9iMHM76xFVJPSkSZbLDV39er3I1Gkaow9yMQin4ptL00Z8rQ76Et";

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