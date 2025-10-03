package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestUserServiceImpl {
    User user = new User();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testAddUser() {
        userService.addUser(user);
        verify(userRepository).addUser(user);
    }

    @Test
    void testGetUsers() {
        List<User> users = List.of(user);
        when(userRepository.getUsers()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(users, result);
    }

    @Test
    void testFindUserById() {
        int testId = 1;
        when(userRepository.findUserByID(testId)).thenReturn(user);
        User result = userService.findUserByID(testId);
        assertEquals(user, result);
    }

    @Test
    void testFindUserByFirstname() {
        String testFirstName = "Test";
        when(userRepository.findUserByFirstName(testFirstName)).thenReturn(user);
        User result = userService.findUserByFirstName(testFirstName);
        assertEquals(user, result);
    }

    @Test
    void testFindUserByLastname() {
        String testLastName = "Test";
        when(userRepository.findUserByLastName(testLastName)).thenReturn(user);
        User result = userService.findUserByLastName(testLastName);
        assertEquals(user, result);
    }

    @Test
    void testFindUserByEmail() {
        String testEmail = "test@example.com";
        when(userRepository.findUserByEmail(testEmail)).thenReturn(user);
        User result = userService.findUserByEmail(testEmail);
        assertEquals(user, result);
    }

    @Test
    void testToggleBanned() {
        int testId = 1;
        boolean testBan = false;
        when(userRepository.toggleBanned(testId)).thenReturn(testBan);
        boolean result = userService.toggleBanned(testId);
        assertEquals(testBan, result);
    }
}
