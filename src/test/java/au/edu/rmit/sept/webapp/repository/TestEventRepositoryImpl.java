package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
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
import java.util.List;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TestEventRepositoryImpl {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private EventRepositoryImpl eventRepository;
    Event event;

    @Test
    void testGetEvents() {
        List<Event> events = eventRepository.getEvents();
        assertTrue(events.size() >= 0);
    }

    @Test
    void testAddEvent() {
        String testTitle = "Example";
        String testDescription = "Describe Event";
        int testOwnerID = 5;
        LocalDateTime testDate = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
        String testLocation = "Place";
        String testCategory = "Thing";
        String testTicketPrice = "$Price";
        String testKeyword1 = "Word";
        String testTag1 = "Tag";
        String testImage1 = "img.png";
        List<String> testKeywords = new ArrayList<>() {{
            add(testKeyword1);
        }};
        List<String> testTags = new ArrayList<>() {{
            add(testTag1);
        }};
        List<String> testImages = new ArrayList<>() {{
            add(testImage1);
        }};

        event = new Event(testTitle, testDescription, testOwnerID, testDate, testLocation, testTicketPrice, testCategory, testKeywords, testTags, testImages);
        eventRepository.addEvent(event);

        Event event2 = eventRepository.findEventbyID(3);

        assertAll("Check added event",
                () -> assertEquals(testTitle, event2.getTitle()),
                () -> assertEquals(testDescription, event2.getDescription()),
                () -> assertEquals(testOwnerID, event2.getOwnerID()),
                () -> assertEquals(testDate, event2.getDate()),
                () -> assertEquals(testLocation, event2.getLocation()),
                () -> assertEquals(testCategory, event2.getCategory()),
                () -> assertEquals(testTicketPrice, event2.getTicketPrice()),
                () -> assertEquals(testKeywords, event2.getKeywords()),
                () -> assertEquals(testTags, event2.getTags()),
                () -> assertEquals(testImages, event2.getImages())
        );
    }

    @Test
    void testGetEvent() {
        int id = 1;
        String title = "Workshop";

        event = eventRepository.getEvent(id, title);

        assertAll("Get first event",
                () -> assertEquals("Workshop", event.getTitle()),
                () -> assertEquals("Learn Basics", event.getDescription()),
                () -> assertEquals(5, event.getOwnerID()),
                () -> assertEquals(LocalDateTime.of(2025, 9, 1, 10, 0), event.getDate()),
                () -> assertEquals("Old Melbourne Gaol, 377 Russell St", event.getLocation()),
                () -> assertEquals("General", event.getCategory()),
                () -> assertEquals("6", event.getTicketPrice()),
                () -> assertEquals(List.of("java", "workshop"), event.getKeywords()),
                () -> assertEquals(List.of("education", "beginner"), event.getTags()),
                () -> assertEquals(List.of("temp.png", "temp.png"), event.getImages())
        );
    }

    @Test
    void testGetEventNotFound() {
        int id = 0;
        String title = "";

        event = eventRepository.getEvent(id, title);
        assertNull(event);
    }

    @Test
    void testGetEventsByFilter() {
        List<Event> nameSearch = eventRepository.getEventsByFilter("Workshop", null, null, null, null);
        List<Event> tagSearch = eventRepository.getEventsByFilter(null, List.of("education", "beginner"), null, null, null);
        List<Event> locationSearch = eventRepository.getEventsByFilter(null, null, "Old Melbourne Gaol, 377 Russell St", null, null);
        List<Event> categorySearch = eventRepository.getEventsByFilter(null, null, null, "General", null);
        List<Event> keywordSearch = eventRepository.getEventsByFilter(null, null, null, null, List.of("java", "workshop"));
        assertAll("All searches give same result",
                () -> assertEquals(1, nameSearch.get(0).getId()),
                () -> assertEquals(1, tagSearch.get(0).getId()),
                () -> assertEquals(1, locationSearch.get(0).getId()),
                () -> assertEquals(1, categorySearch.get(0).getId()),
                () -> assertEquals(1, keywordSearch.get(0).getId())
        );
    }

    @Test
    void testGetEventsByFilterNullOrEmpty() {
        List<Event> searchNull = eventRepository.getEventsByFilter(null, null, null, null, null);
        List<Event> searchEmpty = eventRepository.getEventsByFilter("", List.of(), "", "", List.of());
        assertAll("Empty Search Results",
                () -> assertEquals(eventRepository.getEvents().size(), searchNull.size()),
                () -> assertEquals(eventRepository.getEvents().size(), searchEmpty.size())
        );
    }

    @Test
    void testGetTags() {
        assertNotNull(eventRepository.getTags());
    }

    @Test
    void testGetKeywords() {
        assertNotNull(eventRepository.getKeywords());
    }

    @Test
    void testFindEventByID() {
        event = eventRepository.findEventbyID(1);

        assertAll("Get first event by ID",
                () -> assertEquals("Workshop", event.getTitle()),
                () -> assertEquals("Learn Basics", event.getDescription()),
                () -> assertEquals(5, event.getOwnerID()),
                () -> assertEquals(LocalDateTime.of(2025, 9, 1, 10, 0), event.getDate()),
                () -> assertEquals("Old Melbourne Gaol, 377 Russell St", event.getLocation()),
                () -> assertEquals("General", event.getCategory()),
                () -> assertEquals("6", event.getTicketPrice()),
                () -> assertEquals(List.of("java", "workshop"), event.getKeywords()),
                () -> assertEquals(List.of("education", "beginner"), event.getTags()),
                () -> assertEquals(List.of("temp.png", "temp.png"), event.getImages())
        );
    }

    @Test
    void testFindEventByIDNotFound() {
        event = eventRepository.findEventbyID(0);

        assertNull(event);
    }
/*
    @Test
    void testDeleteEventByID() {
        List<Event> before = eventRepository.getEvents();
        int beforeSize = before.size();
        eventRepository.deleteEventbyID(1);
        List<Event> events = eventRepository.getEvents();
        assertEquals(beforeSize-1, events.size());
    }

    @Test
    void testUpdateEventImages() {
        String testImage = "test.img";
        eventRepository.updateEventImages(1, List.of(testImage));
        event = eventRepository.getEvent(1, "Workshop");
        assertAll("Update Event Image",
            () -> assertEquals(testImage, event.getImages().get(0)),
            () -> assertEquals(1, event.getImages().size())
        );
    }*/

    @Test
    void testDeleteImageForEvent() {
        eventRepository.deleteImageForEvent(1, 0);
        event = eventRepository.getEvents().get(0);
        List<String> imageList = event.getImages();
        assertEquals(1, imageList.size());
    }

    @Test
    void testDeleteImageForEventNotFound() {
        assertAll("Delete Event Image Event Not Found",
                () -> assertFalse(eventRepository.deleteImageForEvent(1, -1)),
                () -> assertFalse(eventRepository.deleteImageForEvent(1, Integer.MAX_VALUE))
        );
    }

    @Test
    void testGetOwnerID() {
        assertEquals(5, eventRepository.getOwnerID(1));
    }

    @Test
    void testGetOwnerIDNotFound() {
        assertNull(eventRepository.getOwnerID(0));
    }

    @Test
    void testAddImageForEvent() {
        event = eventRepository.getEvents().get(0);
        List<String> beforeList = event.getImages();
        int beforeSize = beforeList.size();
        int testId = 1;
        String testImage = "test.img";
        eventRepository.addImageForEvent(testId, testImage);
        List<String> imageList = event.getImages();
        assertEquals(beforeSize, imageList.size());
    }

    @Test
    void testAddImageForEventNotFound() {
        String testImage = "test.img";
        assertFalse(eventRepository.addImageForEvent(-1, testImage));
    }
}

