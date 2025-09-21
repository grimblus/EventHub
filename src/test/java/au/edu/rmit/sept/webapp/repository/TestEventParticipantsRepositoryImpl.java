package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestEventParticipantsRepositoryImpl {
    final private static UserRepository userRepository = new UserRepositoryImpl();
    final private static EventRepository eventRepository = new EventRepositoryImpl();
    final private static EventParticipantsRepository eventParticipantsRepository = new EventParticipantsRepositoryImpl();

    @BeforeEach
    void setUp() throws Exception{
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String cleanSlate = "DROP TABLE IF EXISTS event_participants CASCADE";
            PreparedStatement createTableStatement = connection.prepareStatement(cleanSlate);
            createTableStatement.executeUpdate();
        }
        userRepository.setUsers();
        eventRepository.setEvents();
    }

    @Test
    void testGetEventParticipants() {
        int testEventId = 1;
        assertTrue(eventParticipantsRepository.getEventParticipants(testEventId).isEmpty());
    }

    @Test
    void testAddEventParticipant() {
        int testEventId = 1;
        int testUserId = 1;
        eventParticipantsRepository.addEventParticipant(testEventId, testUserId);
        assertEquals(testUserId, eventParticipantsRepository.getEventParticipants(testEventId).get(0));
    }

    @Test
    void testConstructorCatch() {
        try (MockedStatic<H2DatabaseConnection> mocked = mockStatic(H2DatabaseConnection.class)) {
            mocked.when(H2DatabaseConnection::getConnection).thenThrow(new RuntimeException("Simulated Failure"));
            EventParticipantsRepositoryImpl mockRepo = new EventParticipantsRepositoryImpl();

            assertDoesNotThrow(() -> mockRepo.addEventParticipant(1, 1));
        }
    }

    @Test
    void testCatchBranches() {
        Supplier<Connection> connectionSupplier = () -> {
            throw new RuntimeException("Simulated Failure");
        };
        EventParticipantsRepositoryImpl mockRepo = new EventParticipantsRepositoryImpl(connectionSupplier);

        assertAll("All catch",
                () -> assertDoesNotThrow(() -> mockRepo.getEventParticipants(1)),
                () -> assertDoesNotThrow(() -> mockRepo.addEventParticipant(1, 1))
        );
    }
}
