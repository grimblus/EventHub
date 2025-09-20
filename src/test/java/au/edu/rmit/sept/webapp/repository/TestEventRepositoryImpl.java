package au.edu.rmit.sept.webapp.repository;

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
public class TestEventRepositoryImpl {
    final private static UserRepository userRepository = new UserRepositoryImpl();
    final private static EventRepository eventRepository = new EventRepositoryImpl();
    Event event;

    @BeforeEach
    void setUp() throws Exception{
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String cleanSlate = "DROP TABLE IF EXISTS events CASCADE";
            PreparedStatement createTableStatement = connection.prepareStatement(cleanSlate);
            createTableStatement.executeUpdate();
        }
        userRepository.setUsers();
        eventRepository.setEvents();
    }

    @Test
    void testSetAndGetEvents() {
        List<Event> events = eventRepository.getEvents();
        assertEquals(2, events.size());
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
    void testGetTags() {
        assertEquals(List.of("education", "beginner", "teamwork", "competition"), eventRepository.getTags());
    }

    @Test
    void testGetKeywords() {
        assertEquals(List.of("coding", "java", "workshop", "challenge"), eventRepository.getKeywords());
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
    void testDeleteEventByID() {
        eventRepository.deleteEventbyID(1);
        List<Event> events = eventRepository.getEvents();
        assertEquals(1, events.size());
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
    }

    @Test
    void testDeleteImageForEvent() {
        eventRepository.deleteImageForEvent(1, 0);
        event = eventRepository.getEvents().get(0);
        List<String> imageList = event.getImages();
        assertEquals(1, imageList.size());
    }

    @Test
    void testGetOwnerID() {
        assertEquals(5, eventRepository.getOwnerID(1));
    }

    @Test
    void testConstructorCatch() {
        try (MockedStatic<H2DatabaseConnection> mocked = mockStatic(H2DatabaseConnection.class)) {
            mocked.when(H2DatabaseConnection::getConnection).thenThrow(new RuntimeException("Simulated Failure"));
            EventRepositoryImpl mockRepo = new EventRepositoryImpl();

            assertDoesNotThrow(mockRepo::getEvents);
        }
    }

    @Test
    void testCatchBranches() {
        Supplier<Connection> connectionSupplier = () -> {
            throw new RuntimeException("Simulated Failure");
        };
        EventRepositoryImpl mockRepo = new EventRepositoryImpl(connectionSupplier);

        assertAll("All catch",
                () -> assertDoesNotThrow(mockRepo::setEvents),
                () -> assertDoesNotThrow(() -> mockRepo.addEvent(event)),
                () -> assertDoesNotThrow(() -> mockRepo.getEvents().isEmpty()),
                () -> assertDoesNotThrow(() -> mockRepo.getEvent(1, "Workshop")),
                () -> assertDoesNotThrow(() -> mockRepo.getEventsByFilter("Workshop", null, null, null, null).isEmpty()),
                () -> assertDoesNotThrow(() -> mockRepo.getTags().isEmpty()),
                () -> assertDoesNotThrow(() -> mockRepo.getKeywords().isEmpty()),
                () -> assertDoesNotThrow(() -> mockRepo.findEventbyID(1)),
                () -> assertDoesNotThrow(() -> mockRepo.deleteEventbyID(1)),
                () -> assertDoesNotThrow(() -> mockRepo.updateEventImages(1, List.of("img.png"))),
                () -> assertDoesNotThrow(() -> mockRepo.deleteImageForEvent(1, 0)),
                () -> assertDoesNotThrow(() -> mockRepo.getOwnerID(1))
        );
    }
}

