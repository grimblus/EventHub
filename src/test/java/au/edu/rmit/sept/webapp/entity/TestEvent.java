package au.edu.rmit.sept.webapp.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// make branch for feature
// make feature
// make tests for feature
// once pass tests
// merge into main 

public class TestEvent {
    private Event event = new Event();
    final private User user = new User();
    final private int testId = 5;
    final private String testTitle = "Example";
    final private String testDescription = "Describe Event";
    final private int testOwnerID = 5;
    private List<User> testParticipants = new ArrayList<>() {{
        add(user);
    }};
    @DateTimeFormat(pattern = "yyyy-MM-dd'TT'HH:mm")
    final private LocalDateTime testDate = LocalDateTime.MAX;
    final private String testLocation = "Melbourne";
    final private String testCategory = "Coding";
    final private String testTicketPrice = "$99";
    final private String testKeyword1 = "java";
    final private String testTag1 = "education";
    final private String testImage1 = "img.png";
    final private List<String> testKeywords = new ArrayList<>() {{
        add(testKeyword1);
    }};
    final private List<String> testTags = new ArrayList<>() {{
        add(testTag1);
    }};
    final private List<String> testImages = new ArrayList<>() {{
        add(testImage1);
    }};

    @Test
    void testDefaultConstructor() {
        assertAll("Default constructor",
            () -> assertEquals(0, event.getId()),
            () -> assertEquals("", event.getTitle()),
            () -> assertEquals("", event.getDescription()),
            () -> assertEquals(0, event.getOwnerID()),
            () -> assertEquals(LocalDateTime.MIN, event.getDate()),
            () -> assertEquals("", event.getLocation()),
            () -> assertEquals("", event.getCategory()),
            () -> assertEquals("", event.getTicketPrice()),
            () -> assertEquals(0, event.getKeywords().size()),
            () -> assertEquals(0, event.getTags().size()),
            () -> assertEquals(0, event.getImages().size())
        );
    }

    @Test
    void testNoIDConstructor() {
        event = new Event(testTitle, testDescription, testOwnerID, testDate, testLocation, testTicketPrice, testCategory, testKeywords, testTags, testImages);
        assertAll("No ID constructor",
                () -> assertEquals(0, event.getId()),
                () -> assertEquals(testTitle, event.getTitle()),
                () -> assertEquals(testDescription, event.getDescription()),
                () -> assertEquals(testOwnerID, event.getOwnerID()),
                () -> assertEquals(testDate, event.getDate()),
                () -> assertEquals(testLocation, event.getLocation()),
                () -> assertEquals(testCategory, event.getCategory()),
                () -> assertEquals(testTicketPrice, event.getTicketPrice()),
                () -> assertEquals(testKeywords.size(), event.getKeywords().size()),
                () -> assertEquals(testTags.size(), event.getTags().size()),
                () -> assertEquals(testImages.size(), event.getImages().size())
        );
    }

    @Test
    void testFullConstructor() {
        event = new Event(testId, testTitle, testDescription, testOwnerID, testDate, testLocation, testTicketPrice, testCategory, testKeywords, testTags, testImages);
        assertAll("Full constructor",
                () -> assertEquals(testId, event.getId()),
                () -> assertEquals(testTitle, event.getTitle()),
                () -> assertEquals(testDescription, event.getDescription()),
                () -> assertEquals(testOwnerID, event.getOwnerID()),
                () -> assertEquals(testDate, event.getDate()),
                () -> assertEquals(testLocation, event.getLocation()),
                () -> assertEquals(testCategory, event.getCategory()),
                () -> assertEquals(testTicketPrice, event.getTicketPrice()),
                () -> assertEquals(testKeywords.size(), event.getKeywords().size()),
                () -> assertEquals(testTags.size(), event.getTags().size()),
                () -> assertEquals(testImages.size(), event.getImages().size())
        );
    }

    @Test
    void testGettersAndSetters() {
        event.setId(testId);
        event.setTitle(testTitle);
        event.setDescription(testDescription);
        event.setOwnerID(testOwnerID);
        event.setParticipants(testParticipants);
        event.setDate(testDate);
        event.setLocation(testLocation);
        event.setCategory(testCategory);
        event.setTicketPrice(testTicketPrice);
        event.setKeywords(testKeywords);
        event.setTags(testTags);
        event.setImages(testImages);

        assertAll("Getters and Setters",
                () -> assertEquals(testId, event.getId()),
                () -> assertEquals(testTitle, event.getTitle()),
                () -> assertEquals(testDescription, event.getDescription()),
                () -> assertEquals(testOwnerID, event.getOwnerID()),
                () -> assertEquals(testParticipants, event.getParticipants()),
                () -> assertEquals(testDate, event.getDate()),
                () -> assertEquals(testLocation, event.getLocation()),
                () -> assertEquals(testCategory, event.getCategory()),
                () -> assertEquals(testTicketPrice, event.getTicketPrice()),
                () -> assertEquals(testKeywords.size(), event.getKeywords().size()),
                () -> assertEquals(testTags.size(), event.getTags().size()),
                () -> assertEquals(testImages.size(), event.getImages().size())
        );
    }

    @Test
    void testAddParticipant() {
        event.setParticipants(testParticipants);
        event.addParticipant(user);
        assertEquals(2, event.getParticipants().size());
    }

    @Test
    void testRemoveParticipant() {
        event.setParticipants(testParticipants);
        event.removeParticipant(user);
        assertEquals(0, event.getParticipants().size());
    }

    @Test
    void testAddKeyword() {
        event.setKeywords(testKeywords);
        String testKeyword2 = "TestWord";
        event.addKeyword(testKeyword2);
        assertEquals(2, event.getKeywords().size());
    }

    @Test
    void testRemoveKeyword() {
        event.setKeywords(testKeywords);
        event.removeKeyword(testKeyword1);
        assertEquals(0, event.getKeywords().size());
    }

    @Test
    void testAddTag() {
        event.setTags(testTags);
        String testTag2 = "TestTag";
        event.addTag(testTag2);
        assertEquals(2, event.getTags().size());
    }

    @Test
    void testRemoveTag() {
        event.setTags(testTags);
        event.removeTag(testTag1);
        assertEquals(0, event.getTags().size());
    }

    @Test
    void testAddImage() {
        event.setImages(testImages);
        String testImage2 = "TestImage";
        event.addImage(testImage2);
        assertEquals(2, event.getImages().size());
    }

    @Test
    void testRemoveImage() {
        event.setImages(testImages);
        event.removeImage(testImage1);
        assertEquals(0, event.getImages().size());
    }
}
