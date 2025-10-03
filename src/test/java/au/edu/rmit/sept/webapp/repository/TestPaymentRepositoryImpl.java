package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TestPaymentRepositoryImpl {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private EventRepositoryImpl eventRepository;
    @Autowired
    private PaymentRepositoryImpl paymentRepository;
    Payment payment;

    @Test
    void testGetPayments() {
        List<Payment> payments = paymentRepository.GetAllPayments();
        assertEquals(3, payments.size());
    }

    @Test
    void testAddAndGetPayment() {
        String testPaymentRef = "abcd";
        int testUserID = 1;
        String testAmount = "1234.00";
        LocalDateTime testTime = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
        payment = new Payment(testPaymentRef, testUserID, testAmount, testTime);
        paymentRepository.AddPayment(payment);
        Payment checkPayment = paymentRepository.GetPayment(testPaymentRef);

        assertAll("Add Payment",
                () -> assertEquals(testPaymentRef, checkPayment.getPaymentRef()),
                () -> assertEquals(testUserID, checkPayment.getUser()),
                () -> assertEquals(testAmount, checkPayment.getAmount()),
                () -> assertEquals(testTime, checkPayment.getPaymentTime())
        );
    }

    @Test
    void testGetPaymentNotFound() {
        assertNull(paymentRepository.GetPayment(""));
    }

    @Test
    void testGetUserPayments() {
        assertEquals(2, paymentRepository.GetUserPayments(1).size());
    }
}
