package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class TestPayment {
    private Payment payment;
    private String testPaymentRef = "abcd";
    private int testUserID = 1;
    private String testAmount = "$1234";
    private LocalDateTime testTime = LocalDateTime.MAX;

    @Test
    void testDefaultConstructor() {
        payment = new Payment();
        assertAll(
                () -> assertEquals(payment.getPaymentRef(), ""),
                () -> assertEquals(payment.getUser(), 0),
                () -> assertEquals(payment.getAmount(), ""),
                () -> assertEquals(payment.getPaymentTime(), LocalDateTime.MIN)
        );
    }

    @Test
    void testFullConstructor() {
        payment = new Payment(testPaymentRef, testUserID, testAmount, testTime);
        assertAll(
                () -> assertEquals(payment.getPaymentRef(), testPaymentRef),
                () -> assertEquals(payment.getUser(), testUserID),
                () -> assertEquals(payment.getAmount(), testAmount),
                () -> assertEquals(payment.getPaymentTime(), testTime)
        );
    }
}
