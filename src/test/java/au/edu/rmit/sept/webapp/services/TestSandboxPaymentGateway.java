package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.models.PaymentRequest;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestSandboxPaymentGateway {
    private SandboxPaymentGateway sandboxPaymentGateway;
    private PaymentRequest paymentRequest;
    SandboxPaymentGateway.Result result;
    String testName = "Test";
    String testEmail = "test@example.com";
    Double testAmount = 0.0;
    String testCardNumber = "4242424242424242";
    int testExpMonth = 1;
    int testExpYear = 2030;
    String testCvc = "000";

    @BeforeEach
    void setUp() {
        sandboxPaymentGateway = new SandboxPaymentGateway();
        paymentRequest = new PaymentRequest(testName, testEmail, testAmount, testCardNumber, testExpMonth, testExpYear, testCvc);
    }

    @Test
    void testSuccessCharge() {
        result = sandboxPaymentGateway.charge(paymentRequest);
        paymentRequest.setCardNumber("1111111111111111");
        SandboxPaymentGateway.Result result2 = sandboxPaymentGateway.charge(paymentRequest);
        assertAll("Successfully charged",
                () -> assertTrue(result.success()),
                () -> assertNotNull(result.reference()),
                () -> assertTrue(result.reference().startsWith("REF-")),
                () -> assertTrue(result2.success()),
                () -> assertNotNull(result2.reference()),
                () -> assertTrue(result2.reference().startsWith("REF-"))
        );
    }

    @Test
    void testFailChargeDecline() {
        paymentRequest.setCardNumber("4000000000000002");
        result = sandboxPaymentGateway.charge(paymentRequest);
        assertAll("Card was declined",
                () -> assertFalse(result.success()),
                () -> assertNull(result.reference()),
                () -> assertEquals("Card was declined", result.message())
        );
    }

    @Test
    void testFailChargeExpire() {
        paymentRequest.setExpYear(2000);
        result = sandboxPaymentGateway.charge(paymentRequest);
        assertAll("Card expired",
                () -> assertFalse(result.success()),
                () -> assertNull(result.reference()),
                () -> assertEquals("Card expired", result.message())
        );
    }

    @Test
    void testFailChargeCardNumber() {
        paymentRequest.setCardNumber("0");
        result = sandboxPaymentGateway.charge(paymentRequest);
        paymentRequest.setCardNumber("00000000000000000000000000000000");
        SandboxPaymentGateway.Result result2 = sandboxPaymentGateway.charge(paymentRequest);
        paymentRequest.setCardNumber("40000000000000021");
        SandboxPaymentGateway.Result result3 = sandboxPaymentGateway.charge(paymentRequest);
        assertAll("Invalid card number",
                () -> assertFalse(result.success()),
                () -> assertNull(result.reference()),
                () -> assertEquals("Invalid card number length", result.message()),
                () -> assertFalse(result2.success()),
                () -> assertNull(result2.reference()),
                () -> assertEquals("Invalid card number length", result2.message()),
                () -> assertFalse(result3.success()),
                () -> assertNull(result3.reference()),
                () -> assertEquals("Unsupported test number (try 4242 4242 4242 4242)", result3.message())
        );
    }
}
