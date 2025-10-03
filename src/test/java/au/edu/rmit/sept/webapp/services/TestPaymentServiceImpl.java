package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestPaymentServiceImpl {
    Payment payment = new Payment();
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testGetAllPayments() {
        List<Payment> payments = List.of(payment);
        when(paymentRepository.GetAllPayments()).thenReturn(payments);

        List<Payment> result = paymentService.GetAllPayments();

        assertEquals(payments, result);
    }

    @Test
    void testAddPayment() {
        paymentService.AddPayment(payment);
        verify(paymentRepository).AddPayment(payment);
    }

    @Test
    void testGetUserPayments() {
        List<Payment> payments = List.of(payment);
        when(paymentRepository.GetUserPayments(1)).thenReturn(payments);
        List<Payment> result = paymentService.GetUserPayments(1);
        assertEquals(payments, result);
    }

    @Test
    void testGetPayment() {
        String testRef = "abc";
        when(paymentRepository.GetPayment(testRef)).thenReturn(payment);
        Payment result = paymentService.GetPayment(testRef);
        assertEquals(payment, result);
    }
}
