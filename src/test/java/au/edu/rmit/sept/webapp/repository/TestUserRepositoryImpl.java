package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class TestUserRepositoryImpl {
    private static UserRepository userRepository = new UserRepositoryImpl();
    User user;

    @BeforeEach
    void setUp() throws Exception{
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String cleanSlate = "DROP TABLE IF EXISTS users";
            PreparedStatement createTableStatement = connection.prepareStatement(cleanSlate);
            createTableStatement.executeUpdate();
        }

        userRepository.setUsers();
    }

    @Test
    void testSetAndGetUsers() {
        List<User> users = userRepository.getUsers();
        assertEquals(6, users.size());
    }

    @Test
    void testAddUser() {
        String testFirstName = "Test";
        String testLastName = "User";
        String testEmail = "test@example.com";
        String testPassword = "pass123";
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
        assertEquals("Bingus", user.getFirstName());
    }

    @Test
    void testFindUserByFirstName() {
        user = userRepository.findUserByFirstName("Bingus");
        assertEquals("Bongus", user.getLastName());
    }

    @Test
    void testFindUserByLastName() {
        user = userRepository.findUserByLastName("Bongus");
        assertEquals("Bingus", user.getFirstName());
    }

    @Test
    void testFindUserByEmail() {
        user = userRepository.findUserByEmail("bingus@example.com");
        assertEquals("Bingus", user.getFirstName());
    }

    @Test
    void testToggleBanned() {
        user = userRepository.findUserByID(2);
        boolean banStatus = user.isBanned();
        userRepository.toggleBanned(2);
        user = userRepository.findUserByID(2);
        assertNotEquals(banStatus, user.isBanned());
    }
}
