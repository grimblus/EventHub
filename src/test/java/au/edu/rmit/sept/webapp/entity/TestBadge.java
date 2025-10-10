package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBadge {
    Badge badge = new Badge();
    final private int testBadgeId = 1;
    final private String testBadgeName = "Badge";
    final private String testBadgeDescription = "Congrats";

    @Test
    void testDefaultConstructor() {
        assertAll("Default Constructor",
                () -> assertEquals(0, badge.getBadgeID()),
                () -> assertEquals("", badge.getBadgeName()),
                () -> assertEquals("", badge.getBadgeDescription())
        );
    }

    @Test
    void testFullConstructor() {
        badge = new Badge(testBadgeId, testBadgeName, testBadgeDescription);
        assertAll("Full Constructor",
                () -> assertEquals(testBadgeId, badge.getBadgeID()),
                () -> assertEquals(testBadgeName, badge.getBadgeName()),
                () -> assertEquals(testBadgeDescription, badge.getBadgeDescription())
        );
    }

    @Test
    void testGettersAndSetters() {
        badge = new Badge(testBadgeId, testBadgeName, testBadgeDescription);
        assertAll("Getters and Setters",
                () -> assertEquals(testBadgeId, badge.getBadgeID()),
                () -> assertEquals(testBadgeName, badge.getBadgeName()),
                () -> assertEquals(testBadgeDescription, badge.getBadgeDescription())
        );
    }

    @Test
    void testInstanceOf1() {
        badge = new Badge();
        badge.setBadgeID(1);
        assertTrue(badge.equals(badge));
    }

    @Test
    void testInstanceOf2() {
        Badge badge1 = new Badge();
        badge1.setBadgeID(1);
        Badge badge2 = new Badge();
        badge2.setBadgeID(2);

        assertFalse(badge1.equals(badge2));
    }

    @Test
    void testInstanceOf3() {
        badge = new Badge();
        badge.setBadgeID(testBadgeId);
        Event event = new Event();
        event.setId(1);

        assertFalse(badge.equals(event));
    }

    @Test
    void testInstanceOf4() {
        Badge badge1 = new Badge();
        badge1.setBadgeID(1);
        Badge badge2 = new Badge();
        badge2.setBadgeID(1);

        assertTrue(badge1.equals(badge2));
    }
}
