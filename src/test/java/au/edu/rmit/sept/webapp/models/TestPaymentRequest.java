package au.edu.rmit.sept.webapp.models;

import jakarta.validation.constraints.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPaymentRequest {
    PaymentRequest paymentRequest = new PaymentRequest();
    final private String testCardholderName = "Place Holder";
    final private String testEmail = "temp@example.com";
    final private Double testAmount = 100.0;
    final private String testCardNumber = "4578543105648";
    final private int testExpMonth = 5;
    final private int testExpYear = 2028;
    final private String testCvc = "946";

    @Test
    void testDefaultConstructor() {
        assertAll(
                () -> assertEquals("", paymentRequest.getCardholderName()),
                () -> assertEquals("", paymentRequest.getEmail()),
                () -> assertEquals(0.0, paymentRequest.getAmount()),
                () -> assertEquals("", paymentRequest.getCardNumber()),
                () -> assertEquals(0, paymentRequest.getExpMonth()),
                () -> assertEquals(0, paymentRequest.getExpYear()),
                () -> assertEquals("", paymentRequest.getCvc())
        );
    }

    @Test
    void testFullConstructor() {
        paymentRequest = new PaymentRequest(testCardholderName, testEmail, testAmount, testCardNumber, testExpMonth, testExpYear, testCvc);
        assertAll(
                () -> assertEquals(testCardholderName, paymentRequest.getCardholderName()),
                () -> assertEquals(testEmail, paymentRequest.getEmail()),
                () -> assertEquals(testAmount, paymentRequest.getAmount()),
                () -> assertEquals(testCardNumber, paymentRequest.getCardNumber()),
                () -> assertEquals(testExpMonth, paymentRequest.getExpMonth()),
                () -> assertEquals(testExpYear, paymentRequest.getExpYear()),
                () -> assertEquals(testCvc, paymentRequest.getCvc())
        );
    }

    @Test
    void testGettersAndSetters() {
        paymentRequest.setCardholderName(testCardholderName);
        paymentRequest.setEmail(testEmail);
        paymentRequest.setAmount(testAmount);
        paymentRequest.setCardNumber(testCardNumber);
        paymentRequest.setExpMonth(testExpMonth);
        paymentRequest.setExpYear(testExpYear);
        paymentRequest.setCvc(testCvc);

        assertAll(
                () -> assertEquals(testCardholderName, paymentRequest.getCardholderName()),
                () -> assertEquals(testEmail, paymentRequest.getEmail()),
                () -> assertEquals(testAmount, paymentRequest.getAmount()),
                () -> assertEquals(testCardNumber, paymentRequest.getCardNumber()),
                () -> assertEquals(testExpMonth, paymentRequest.getExpMonth()),
                () -> assertEquals(testExpYear, paymentRequest.getExpYear()),
                () -> assertEquals(testCvc, paymentRequest.getCvc())
        );
    }
}
