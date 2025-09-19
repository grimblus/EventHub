package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestBadge {
    Badge badge = new Badge();
    private int    testBadgeId = 1;
    private String testBadgeName = "Badge";
    private String testBadgeDescription = "Congrats";
    private String testBadgeImage = "img.png";
    private HashMap<String, Boolean> testBadgeFlags = new HashMap<>();
    private String testFlagCondition = "exists";
    private Boolean testSetFlag = true;

    @Test
    void testDefaultConstructor() {
        assertAll("Default Constructor",
                () -> assertEquals(badge.getBadgeID(), 0),
                () -> assertEquals(badge.getBadgeName(), ""),
                () -> assertEquals(badge.getBadgeDescription(), ""),
                () -> assertEquals(badge.getBadgeImage(), ""),
                () -> assertEquals(badge.getBadgeFlags().size(), 0)
        );
    }

    @Test
    void testFullConstructor() {
        badge = new Badge(testBadgeId, testBadgeName, testBadgeDescription, testBadgeImage);
        assertAll("Full Constructor",
                () -> assertEquals(badge.getBadgeID(), testBadgeId),
                () -> assertEquals(badge.getBadgeName(), testBadgeName),
                () -> assertEquals(badge.getBadgeDescription(), testBadgeDescription),
                () -> assertEquals(badge.getBadgeImage(), testBadgeImage),
                () -> assertEquals(badge.getBadgeFlags().size(), 0)
        );
    }

    @Test
    void testGettersAndSetters() {
        badge.setbadgeID(testBadgeId);
        badge.setBadgeName(testBadgeName);
        badge.setBadgeDescription(testBadgeDescription);
        badge.setBadgeImage(testBadgeImage);
        badge.setBadgeFlags(testBadgeFlags);
        assertAll("Getters and Setters",
                () -> assertEquals(badge.getBadgeID(), testBadgeId),
                () -> assertEquals(badge.getBadgeName(), testBadgeName),
                () -> assertEquals(badge.getBadgeDescription(), testBadgeDescription),
                () -> assertEquals(badge.getBadgeImage(), testBadgeImage),
                () -> assertEquals(badge.getBadgeFlags().size(), 0)
        );
    }

    @Test
    void testSetBadgeFlagCondition() {
        badge.setBadgeFlagCondition(testFlagCondition, testSetFlag);
        assertAll("Set badge Flag Condition",
                () -> assertEquals(badge.getBadgeFlags().size(), 1)
        );
    }

    @Test
    void testRemoveBadgeFlagCondition() {
        badge.setBadgeFlagCondition(testFlagCondition, testSetFlag);
        badge.removeBadgeFlagCondition(testFlagCondition);
        assertEquals(badge.getBadgeFlags().size(), 0);
    }
}
