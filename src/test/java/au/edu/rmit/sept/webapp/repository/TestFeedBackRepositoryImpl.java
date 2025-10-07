package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TestFeedBackRepositoryImpl {
    @Autowired
    private FeedbackRepositoryImpl feedbackRepository;

    @Test
    void testAdd() {
        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setUserId(1);
        feedback.setEventId(1);
        feedback.setEventTitle("Cool Event");
        feedback.setRating(6);
        feedback.setComment("Super cool awesome fun fantastic amazing intriguing feedback");
        feedback.setCreatedAt(LocalDateTime.MIN);

        assertTrue(feedbackRepository.add(feedback));
    }

    @Test
    void testFindByUserId() {
        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setUserId(1);
        feedback.setEventId(1);
        feedback.setEventTitle("Cool Event");
        feedback.setRating(6);
        feedback.setComment("Super cool awesome fun fantastic amazing intriguing feedback");
        feedback.setCreatedAt(LocalDateTime.MIN);
        feedbackRepository.add(feedback);

        List<Feedback> feedbackList = feedbackRepository.findByUserId(1);
        assertTrue(feedbackList.size() >= 1);
    }

    @Test
    void testFindByUserIdNotFound() {
        List<Feedback> feedbackList = feedbackRepository.findByUserId(0);
        assertTrue(feedbackList.isEmpty());
    }
}
