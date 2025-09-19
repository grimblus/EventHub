package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private User user;
    private String testFirstName = "Bingus";
    private String testLastName = "Bongus";
    private String testEmail = "bingus@example.com";
    private String testPassword = "s3cur3pass";
    private User.permissions testRole = User.permissions.ADMIN;

    @Test
    void testDefaultConstructor() {
        user = new User();
        assertAll(
            () -> assertNull(user.getId()),
            () -> assertEquals(user.getFirstName(), ""),
            () -> assertEquals(user.getLastName(), ""),
            () -> assertEquals(user.getEmail(), ""),
            () -> assertEquals(user.getPassword(), ""),
            () -> assertEquals(user.getPermissionLevel(), User.permissions.STUDENT)
        );
    }

}
