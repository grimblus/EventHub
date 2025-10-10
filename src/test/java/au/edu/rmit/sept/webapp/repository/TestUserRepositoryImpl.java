package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
public class TestUserRepositoryImpl {
    @Autowired
    private UserRepository userRepository;
    User user;

    @Test
    void testGetUsers() {
        List<User> users = userRepository.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void testAddUser() {
        String testFirstName = "Bingus";
        String testLastName = "Bongus";
        String testEmail = "bingus@example.com";
        String testPassword = "s3cur3pass";
        User.permissions testRole = User.permissions.ADMIN;
        user = new User(testFirstName, testLastName, testPassword, testEmail, testRole);
        userRepository.addUser(user);

        User user2 = userRepository.findUserByEmail(testEmail);

        assertAll("Check added user",
                () -> assertEquals(testFirstName, user2.getFirstName()),
                () -> assertEquals(testLastName, user2.getLastName()),
                () -> assertEquals(testEmail, user2.getEmail()),
                () -> assertEquals(testPassword, user2.getPassword()),
                () -> assertEquals(testRole, user2.getPermissionLevel())
        );
    }

    @Test
    void testFindUserByID() {
        user = userRepository.findUserByID(2);
        assertEquals("Andrew", user.getFirstName());
    }

    @Test
    void testFindUserByIDNotFound() {
        user = userRepository.findUserByID(0);
        assertNull(user);
    }

    @Test
    void testFindUserByFirstName() {
        user = userRepository.findUserByFirstName("Aaron");
        assertEquals("Lay", user.getLastName());
    }

    @Test
    void testFindUserByFirstNameNotFound() {
        user = userRepository.findUserByFirstName("");
        assertNull(user);
    }

    @Test
    void testFindUserByLastName() {
        user = userRepository.findUserByLastName("Watson");
        assertEquals("Brody", user.getFirstName());
    }

    @Test
    void testFindUserByLastNameNotFound() {
        user = userRepository.findUserByLastName("");
        assertNull(user);
    }

    @Test
    void testFindUserByEmail() {
        user = userRepository.findUserByEmail("tran.aaron@example.com");
        assertEquals("Aaron", user.getFirstName());
    }

    @Test
    void testFindUserByEmailNotFound() {
        user = userRepository.findUserByEmail("");
        assertNull(user);
    }

    @Test
    void testToggleBanned() {
        user = userRepository.findUserByID(2);
        boolean banStatus = user.isBanned();
        userRepository.toggleBanned(2);
        user = userRepository.findUserByID(2);
        assertNotEquals(banStatus, user.isBanned());
    }

    @Test
    void testToggleBannedNotFound() {
        assertFalse(userRepository.toggleBanned(0));
    }

    @Test
    void testSaveAndGetEvent() {
        int userId = 1;
        int eventId = 1;
        userRepository.saveEvent(userId, eventId);
        List<Event> savedEvents = userRepository.getSavedEvents(userId);
        assertFalse(savedEvents.isEmpty());
    }

    @Test 
    void testGetSavedEventsEmpty() {
        List<Event> events = userRepository.getSavedEvents(2);
        assertEquals(0, events.size());
    }

    @Test
    void testUnsaveEvent() {
        int userId = 1;
        int eventId = 1;
        userRepository.unsaveEvent(userId, eventId);
        List<Event> savedEvents = userRepository.getSavedEvents(userId);
        assertTrue(savedEvents.isEmpty());
    }
}
