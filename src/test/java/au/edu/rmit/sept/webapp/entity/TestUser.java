package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private User user;
    private Event event = new Event();
    private int testID = 1;
    private String testFirstName = "Bingus";
    private String testLastName = "Bongus";
    private String testEmail = "bingus@example.com";
    private String testPassword = "s3cur3pass";
    private User.permissions testRole = User.permissions.ADMIN;
    private boolean testBanned = true;
    private ArrayList<Event> testRSVPList = new ArrayList<Event>() {{
        add(event);
    }};

    @Test
    void testDefaultConstructor() {
        user = new User();
        assertAll(
            () -> assertNull(user.getId()),
            () -> assertEquals(user.getFirstName(), ""),
            () -> assertEquals(user.getLastName(), ""),
            () -> assertEquals(user.getEmail(), ""),
            () -> assertEquals(user.getPassword(), ""),
            () -> assertEquals(user.getPermissionLevel(), User.permissions.STUDENT),
            () -> assertTrue(!user.isBanned()),
            () -> assertTrue(user.getRsvpList().isEmpty())
        );
    }

    @Test
    void testNoIDConstructor() {
        user = new User(testFirstName, testLastName, testEmail, testPassword, testRole);
        assertAll(
            () -> assertNull(user.getId()),
            () -> assertEquals(user.getFirstName(), testFirstName),
            () -> assertEquals(user.getLastName(), testLastName),
            () -> assertEquals(user.getEmail(), testEmail),
            () -> assertEquals(user.getPassword(), testPassword),
            () -> assertEquals(user.getPermissionLevel(), testRole),
            () -> assertTrue(!user.isBanned()),
            () -> assertTrue(user.getRsvpList().isEmpty())
        );
    }

    @Test
    void testFullConstructor() {
        user = new User(testID, testFirstName, testLastName, testEmail, testPassword, testRole, testBanned, testRSVPList);
        assertAll(
                () -> assertEquals(user.getId(), testID),
                () -> assertEquals(user.getFirstName(), testFirstName),
                () -> assertEquals(user.getLastName(), testLastName),
                () -> assertEquals(user.getEmail(), testEmail),
                () -> assertEquals(user.getPassword(), testPassword),
                () -> assertEquals(user.getPermissionLevel(), testRole),
                () -> assertEquals(user.isBanned(), testBanned),
                () -> assertEquals(user.getRsvpList(), testRSVPList)
        );
    }
}
