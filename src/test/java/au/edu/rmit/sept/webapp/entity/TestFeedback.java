package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestFeedback {
    private Feedback feedback = new Feedback();
    final private Integer testId = 1;
    final private Integer testUserId = 1;
    final private Integer testEventId = 1;
    final private String  testEventTitle = "Event";
    final private Integer testRating = 3;
    final private String  testComment = "Comment";
    final private LocalDateTime testCreatedAt = LocalDateTime.MAX;

    @Test
    void testDefaultConstructor() {
        assertAll("Default Constructor",
                () -> assertEquals(0, feedback.getId()),
                () -> assertEquals(0, feedback.getUserId()),
                () -> assertEquals(0, feedback.getEventId()),
                () -> assertEquals("", feedback.getEventTitle()),
                () -> assertEquals(0, feedback.getRating()),
                () -> assertEquals("", feedback.getComment()),
                () -> assertEquals(LocalDateTime.MIN, feedback.getCreatedAt())
        );
    }

    @Test
    void testFullConstructor() {
        feedback = new Feedback(testId, testUserId, testEventId, testEventTitle, testRating, testComment, testCreatedAt);
        assertAll("Full Constructor",
                () -> assertEquals(testId, feedback.getId()),
                () -> assertEquals(testUserId, feedback.getUserId()),
                () -> assertEquals(testEventId, feedback.getEventId()),
                () -> assertEquals(testEventTitle, feedback.getEventTitle()),
                () -> assertEquals(testRating, feedback.getRating()),
                () -> assertEquals(testComment, feedback.getComment()),
                () -> assertEquals(testCreatedAt, feedback.getCreatedAt())
        );
    }

    @Test
    void testSettersAndGetters() {
        feedback.setId(testId);
        feedback.setUserId(testUserId);
        feedback.setEventId(testEventId);
        feedback.setEventTitle(testEventTitle);
        feedback.setRating(testRating);
        feedback.setComment(testComment);
        feedback.setCreatedAt(testCreatedAt);

        assertAll("Getters and Setters",
                () -> assertEquals(testId, feedback.getId()),
                () -> assertEquals(testUserId, feedback.getUserId()),
                () -> assertEquals(testEventId, feedback.getEventId()),
                () -> assertEquals(testEventTitle, feedback.getEventTitle()),
                () -> assertEquals(testRating, feedback.getRating()),
                () -> assertEquals(testComment, feedback.getComment()),
                () -> assertEquals(testCreatedAt, feedback.getCreatedAt())
        );
    }
}