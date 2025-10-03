package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.EventParticipantsRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestEventParticipantsServiceImpl {
    @Mock
    private EventParticipantsRepository eventParticipantsRepository;

    @InjectMocks
    private EventParticipantsServiceImpl eventParticipantsService;

    @Test
    void testGetEventParticipants() {
        int testEventId = 1;
        User testUser = new User();
        testUser.setFirstName("john");
        List<User> participants = List.of(testUser);
        when(eventParticipantsRepository.getEventParticipants(testEventId)).thenReturn(participants);

        List<User> result = eventParticipantsService.getEventParticipants(testEventId);

        assertEquals("john", result.get(0).getFirstName());
    }

    @Test
    void testAddEventParticipant() {
        int testEventId = 1;
        int testUserId = 1;
        eventParticipantsService.addEventParticipant(testEventId, testUserId);
        verify(eventParticipantsRepository).addEventParticipant(testEventId, testUserId);
    }
}
