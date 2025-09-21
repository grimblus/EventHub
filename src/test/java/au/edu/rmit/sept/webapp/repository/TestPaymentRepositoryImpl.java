package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class TestPaymentRepositoryImpl {
    final private static UserRepository userRepository = new UserRepositoryImpl();
    final private static EventRepository eventRepository = new EventRepositoryImpl();
    final private static PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    Payment payment;

    @BeforeEach
    void setUp() throws Exception{
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String cleanSlate = "DROP TABLE IF EXISTS payments";
            PreparedStatement createTableStatement = connection.prepareStatement(cleanSlate);
            createTableStatement.executeUpdate();
        }
        userRepository.setUsers();
        eventRepository.setEvents();
        paymentRepository.setPayments();
    }

    @Test
    void testSetAndGetPayments() {
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

    @Test
    void testConstructorCatch() {
        try (MockedStatic<H2DatabaseConnection> mocked = mockStatic(H2DatabaseConnection.class)) {
            mocked.when(H2DatabaseConnection::getConnection).thenThrow(new RuntimeException("Simulated Failure"));
            PaymentRepository mockRepo = new PaymentRepositoryImpl();

            assertDoesNotThrow(mockRepo::GetAllPayments);
        }
    }

    @Test
    void testCatchBranches() {
        Supplier<Connection> connectionSupplier = () -> {
            throw new RuntimeException("Simulated Failure");
        };
        PaymentRepositoryImpl mockRepo = new PaymentRepositoryImpl(connectionSupplier);

        assertAll("All catch",
                () -> assertDoesNotThrow(mockRepo::setPayments),
                () -> assertDoesNotThrow(mockRepo::GetAllPayments),
                () -> assertDoesNotThrow(() -> mockRepo.AddPayment(payment)),
                () -> assertDoesNotThrow(() -> mockRepo.GetUserPayments(1)),
                () -> assertDoesNotThrow(() -> mockRepo.GetPayment("test"))
        );
    }
}
