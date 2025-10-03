package au.edu.rmit.sept.webapp.entity;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestBadge {
    Badge badge = new Badge();
    final private int testBadgeId = 1;
    final private String testBadgeName = "Badge";
    final private String testBadgeDescription = "Congrats";
    final private String testBadgeImage = "img.png";
    final private HashMap<String, Boolean> testBadgeFlags = new HashMap<>();
    final private String testFlagCondition1 = "exists";
    final private Boolean testSetFlag = true;

    // @Test
    // void testDefaultConstructor() {
    //     assertAll("Default Constructor",
    //             () -> assertEquals(0, badge.getBadgeID()),
    //             () -> assertEquals("", badge.getBadgeName()),
    //             () -> assertEquals("", badge.getBadgeDescription()),
    //             () -> assertEquals("", badge.getBadgeImage()),
    //             () -> assertEquals(0, badge.getBadgeFlags().size())
    //     );
    // }

    // @Test
    // void testFullConstructor() {
    //     badge = new Badge(testBadgeId, testBadgeName, testBadgeDescription, testBadgeImage);
    //     assertAll("Full Constructor",
    //             () -> assertEquals(testBadgeId, badge.getBadgeID()),
    //             () -> assertEquals(testBadgeName, badge.getBadgeName()),
    //             () -> assertEquals(testBadgeDescription, badge.getBadgeDescription()),
    //             () -> assertEquals(testBadgeImage, badge.getBadgeImage()),
    //             () -> assertEquals(0, badge.getBadgeFlags().size())
    //     );
    // }

    // @Test
    // void testGettersAndSetters() {
    //     badge.setbadgeID(testBadgeId);
    //     badge.setBadgeName(testBadgeName);
    //     badge.setBadgeDescription(testBadgeDescription);
    //     badge.setBadgeImage(testBadgeImage);
    //     badge.setBadgeFlags(testBadgeFlags);
    //     assertAll("Getters and Setters",
    //             () -> assertEquals(testBadgeId, badge.getBadgeID()),
    //             () -> assertEquals(testBadgeName, badge.getBadgeName()),
    //             () -> assertEquals(testBadgeDescription, badge.getBadgeDescription()),
    //             () -> assertEquals(testBadgeImage, badge.getBadgeImage()),
    //             () -> assertEquals(0, badge.getBadgeFlags().size())
    //     );
    // }

    // @Test
    // void testSetBadgeFlagCondition() {
    //     badge.setBadgeFlagCondition(testFlagCondition1, testSetFlag);
    //     assertAll("Set badge Flag Condition",
    //             () -> assertEquals(1, badge.getBadgeFlags().size())
    //     );
    // }

    // @Test
    // void testRemoveBadgeFlagConditionSuccess() {
    //     badge.setBadgeFlagCondition(testFlagCondition1, testSetFlag);
    //     badge.removeBadgeFlagCondition(testFlagCondition1);
    //     assertEquals(0, badge.getBadgeFlags().size());
    // }

    // @Test
    // void testRemoveBadgeFlagConditionFail() {
    //     String testFlagCondition2 = "fails";
    //     badge.setBadgeFlagCondition(testFlagCondition1, testSetFlag);
    //     badge.removeBadgeFlagCondition(testFlagCondition2);
    //     assertEquals(1, badge.getBadgeFlags().size());
    // }
}
