package au.edu.rmit.sept.webapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TestEventParticipantsRepositoryImpl {
    @Autowired
    private EventParticipantsRepositoryImpl eventParticipantsRepository;

    @Test
    void testGetEventParticipants() {
        int testEventId = 1;
        assertFalse(eventParticipantsRepository.getEventParticipants(testEventId).isEmpty());
    }

    @Test
    void testAddEventParticipant() {
        int testEventId = 1;
        int testUserId = 1;
        eventParticipantsRepository.addEventParticipant(testEventId, testUserId);
        assertEquals(testUserId, eventParticipantsRepository.getEventParticipants(testEventId).get(0));
    }
}
