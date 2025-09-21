package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Supplier;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class TestUserRepositoryImpl {
    final private static UserRepository userRepository = new UserRepositoryImpl();
    User user;

    @BeforeEach
    void setUp() throws Exception{
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String cleanSlate = "DROP TABLE IF EXISTS users CASCADE";
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
    void testFindUserByIDNotFound() {
        user = userRepository.findUserByID(0);
        assertNull(user);
    }

    @Test
    void testFindUserByFirstName() {
        user = userRepository.findUserByFirstName("Bingus");
        assertEquals("Bongus", user.getLastName());
    }

    @Test
    void testFindUserByFirstNameNotFound() {
        user = userRepository.findUserByFirstName("");
        assertNull(user);
    }

    @Test
    void testFindUserByLastName() {
        user = userRepository.findUserByLastName("Bongus");
        assertEquals("Bingus", user.getFirstName());
    }

    @Test
    void testFindUserByLastNameNotFound() {
        user = userRepository.findUserByLastName("");
        assertNull(user);
    }

    @Test
    void testFindUserByEmail() {
        user = userRepository.findUserByEmail("bingus@example.com");
        assertEquals("Bingus", user.getFirstName());
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
    void testConstructorCatch() {
        try (MockedStatic<H2DatabaseConnection> mocked = mockStatic(H2DatabaseConnection.class)) {
            mocked.when(H2DatabaseConnection::getConnection).thenThrow(new RuntimeException("Simulated Failure"));
            UserRepository mockRepo = new UserRepositoryImpl();

            assertDoesNotThrow(mockRepo::getUsers);
        }
    }

    @Test
    void testCatchBranches() {
        Supplier<Connection> connectionSupplier = () -> {
            throw new RuntimeException("Simulated Failure");
        };
        UserRepositoryImpl mockRepo = new UserRepositoryImpl(connectionSupplier);

        assertAll("All catch",
                () -> assertDoesNotThrow(mockRepo::setUsers),
                () -> assertDoesNotThrow(() -> mockRepo.addUser(user)),
                () -> assertDoesNotThrow(() -> mockRepo.getUsers().size()),
                () -> assertDoesNotThrow(() -> mockRepo.findUserByID(2)),
                () -> assertDoesNotThrow(() -> mockRepo.findUserByFirstName("bingus")),
                () -> assertDoesNotThrow(() -> mockRepo.findUserByLastName("bongus")),
                () -> assertDoesNotThrow(() -> mockRepo.findUserByEmail("bingus@example.com")),
                () -> assertDoesNotThrow(() -> mockRepo.toggleBanned(2))
        );
    }
}
