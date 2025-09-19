package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private User user = new User();
    final private Event testEvent = new Event();
    final private int testID = 5;
    final private String testFirstName = "Bingus";
    final private String testLastName = "Bongus";
    final private String testEmail = "bingus@example.com";
    final private String testPassword = "s3cur3pass";
    final private User.permissions testRole = User.permissions.ADMIN;
    final private boolean testBanned = true;
    final private ArrayList<Event> testRSVPList = new ArrayList<Event>();

    @Test
    void testDefaultConstructor() {
        assertAll("Default Constructor",
            () -> assertEquals(0, user.getId()),
            () -> assertEquals("", user.getFirstName()),
            () -> assertEquals("", user.getLastName()),
            () -> assertEquals("", user.getEmail()),
            () -> assertEquals("", user.getPassword()),
            () -> assertEquals(User.permissions.STUDENT, user.getPermissionLevel()),
            () -> assertFalse(user.isBanned()),
            () -> assertTrue(user.getRsvpList().isEmpty())
        );
    }

    @Test
    void testNoIDConstructor() {
        user = new User(testFirstName, testLastName, testPassword, testEmail, testRole);
        assertAll("No ID Constructor",
                () -> assertEquals(0, user.getId()),
                () -> assertEquals(testFirstName, user.getFirstName()),
                () -> assertEquals(testLastName, user.getLastName()),
                () -> assertEquals(testEmail, user.getEmail()),
                () -> assertEquals(testPassword, user.getPassword()),
                () -> assertEquals(testRole, user.getPermissionLevel()),
                () -> assertFalse(user.isBanned()),
                () -> assertTrue(user.getRsvpList().isEmpty())
        );
    }

    @Test
    void testFullConstructor() {
        user = new User(testID, testFirstName, testLastName, testPassword, testEmail, testRole, testBanned);
        assertAll("Full Constructor",
                () -> assertEquals(testID, user.getId()),
                () -> assertEquals(testFirstName, user.getFirstName()),
                () -> assertEquals(testLastName, user.getLastName()),
                () -> assertEquals(testEmail, user.getEmail()),
                () -> assertEquals(testPassword, user.getPassword()),
                () -> assertEquals(testRole, user.getPermissionLevel()),
                () -> assertTrue(user.isBanned()),
                () -> assertTrue(user.getRsvpList().isEmpty())
        );
    }

    @Test
    void testSettersAndGetters() {
        user.setId(testID);
        user.setFirstName(testFirstName);
        user.setLastName(testLastName);
        user.setPassword(testPassword);
        user.setEmail(testEmail);
        user.setPermissionLevel(testRole);
        user.setBanned(testBanned);
        user.setRsvpList(testRSVPList);

        assertAll("Getters and Setters",
                () -> assertEquals(testID, user.getId()),
                () -> assertEquals(testFirstName, user.getFirstName()),
                () -> assertEquals(testLastName, user.getLastName()),
                () -> assertEquals(testEmail, user.getEmail()),
                () -> assertEquals(testPassword, user.getPassword()),
                () -> assertEquals(testRole, user.getPermissionLevel()),
                () -> assertTrue(user.isBanned()),
                () -> assertTrue(user.getRsvpList().isEmpty())
        );
    }

    @Test
    void testAddRsvp() {
        user.addRsvp(testEvent);

        assertTrue(user.getRsvpList().contains(testEvent));
    }
}
