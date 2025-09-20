package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.function.Supplier;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
public class TestEventParticipantsRepositoryImpl {
    final private static EventParticipantsRepositoryImpl eventParticipantsRepository = new EventParticipantsRepositoryImpl();

    @Test
    void testAddEventParticipant() {
        int testEventId = 1;
        int testUserId = 1;
        eventParticipantsRepository.addEventParticipant(testEventId, testUserId);
    }
}
