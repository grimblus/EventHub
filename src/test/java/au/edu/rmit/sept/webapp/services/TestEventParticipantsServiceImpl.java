package au.edu.rmit.sept.webapp.services;

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
        List<Integer> participants = List.of(testEventId);
        when(eventParticipantsRepository.getEventParticipants(testEventId)).thenReturn(participants);

        List<Integer> result = eventParticipantsService.getEventParticipants(testEventId);

        assertEquals(participants, result);
    }

    @Test
    void testAddEventParticipant() {
        int testEventId = 1;
        int testUserId = 1;
        eventParticipantsService.addEventParticipant(testEventId, testUserId);
        verify(eventParticipantsRepository).addEventParticipant(testEventId, testUserId);
    }
}
