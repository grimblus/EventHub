package au.edu.rmit.sept.webapp.repository;
import au.edu.rmit.sept.webapp.entity.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
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
public class TestUserInterestsRepositoryImpl {

    @Autowired
    private UserInterestsRepository userInterestsRepository;

    @Test
    void getUserInterests() {
        HashSet<String> events = userInterestsRepository.getUserInterests(2);
        assertEquals(0, events.size());
    }

    @Test
    void addUserInterests() {

        ArrayList<String> interests = new ArrayList<>(List.of("GENERAL", "CODING", "ACCESSIBLE"));
        userInterestsRepository.addUserInterests(1, interests);
        HashSet<String> events = userInterestsRepository.getUserInterests(1);
        assertTrue(events.containsAll(interests));

    }

}
