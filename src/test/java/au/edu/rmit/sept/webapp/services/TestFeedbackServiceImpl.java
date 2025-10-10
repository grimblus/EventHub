package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Feedback;
import au.edu.rmit.sept.webapp.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestFeedbackServiceImpl {
    @Mock
    FeedbackRepository feedbackRepository;

    @InjectMocks
    FeedbackServiceImpl feedbackService;

    @Test
    public void testSubmitForFeedback() {
        when(feedbackRepository.add(any(Feedback.class))).thenReturn(true);

        boolean result = feedbackService.submitForEvent(1, 1, "Event", 1, "Comment");

        assertTrue(result);
        verify(feedbackRepository).add(any(Feedback.class));
    }

    @Test
    public void testFindByUserId() {
        Feedback feedback = new Feedback();
        List<Feedback> feedbackList = List.of(feedback);
        when(feedbackRepository.findByUserId(1)).thenReturn(feedbackList);
        List<Feedback> result = feedbackService.findByUserId(1);
        assertEquals(feedbackList,result);
    }
}
