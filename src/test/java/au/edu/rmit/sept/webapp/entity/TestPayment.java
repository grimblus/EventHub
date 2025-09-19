package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class TestPayment {
    private Payment payment = new Payment();;
    final private String testPaymentRef = "abcd";
    final private int testUserID = 1;
    final private String testAmount = "$1234";
    final private LocalDateTime testTime = LocalDateTime.MAX;

    @Test
    void testGettersAndSetters() {
        payment.setPaymentRef(testPaymentRef);
        payment.setUser(testUserID);
        payment.setAmount(testAmount);
        payment.setPaymentTime(testTime);

        assertAll("Getters and Setters",
                () -> assertEquals(testPaymentRef, payment.getPaymentRef()),
                () -> assertEquals(testUserID, payment.getUser()),
                () -> assertEquals(testAmount, payment.getAmount()),
                () -> assertEquals(testTime, payment.getPaymentTime())
        );
    }

    @Test
    void testDefaultConstructor() {
        assertAll("Default Constructor",
                () -> assertEquals("", payment.getPaymentRef()),
                () -> assertEquals(0, payment.getUser()),
                () -> assertEquals("", payment.getAmount()),
                () -> assertEquals(LocalDateTime.MIN, payment.getPaymentTime())
        );
    }

    @Test
    void testFullConstructor() {
        payment = new Payment(testPaymentRef, testUserID, testAmount, testTime);
        assertAll("Full Constructor",
                () -> assertEquals(testPaymentRef, payment.getPaymentRef()),
                () -> assertEquals(testUserID, payment.getUser()),
                () -> assertEquals(testAmount, payment.getAmount()),
                () -> assertEquals(testTime, payment.getPaymentTime())
        );
    }
}
