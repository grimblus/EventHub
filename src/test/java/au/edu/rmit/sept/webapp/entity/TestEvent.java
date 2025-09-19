package au.edu.rmit.sept.webapp.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

// make branch for feature
// make feature
// make tests for feature
// once pass tests
// merge into main 

public class TestEvent {
    @Test
    void testDefaultConstructor() {
        Event event = new Event();
                assertAll(
            () -> assertEquals(event.getId(), -99),
            () -> assertEquals(event.getTitle(), ""),
            () -> assertEquals(event.getDescription(), ""),
            () -> assertEquals(event.getOwnerID(), -99),
            () -> assertNull(event.getDate()),
            () -> assertEquals(event.getLocation(),""),
            () -> assertEquals(event.getTicketPrice(),);
        );
    }

}
