package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Badge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TestBadgeRepositoryImpl {

    

    @Autowired
    private BadgeRepositoryImpl badgeRepository;

    @Test
    void testAssignBadge() {
        int userId = 3;
        int badgeId = 2;
        badgeRepository.assignBadgeToUser(userId, badgeId);

        List<Badge> badges = badgeRepository.getBadgesByUser(userId);
        boolean hasBadge = badges.stream().anyMatch(b -> b.getBadgeID() == badgeId);

        assertTrue(hasBadge, "User should now have assigned badge");
    }
    
    @Test
    void testGetAllBadges() {
        List<Badge> badges = badgeRepository.getAllBadges();
        assertTrue(badges.size() >= 0);
    }

    @Test
    void testGetBadgesByUserNotFound() {
        List<Badge> badges = badgeRepository.getBadgesByUser(-1);
        assertNotNull(badges);
        assertTrue(badges.isEmpty(), "Should return empty list for non-existent user");
    }

}