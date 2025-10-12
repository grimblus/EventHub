package au.edu.rmit.sept.webapp.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TestEventParticipantsRepositoryImpl {
    @Autowired
    private EventParticipantsRepositoryImpl eventParticipantsRepository;
/*
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
        assertTrue(eventParticipantsRepository.getEventParticipants(testEventId).size() >= 0);
    }
 */
}
