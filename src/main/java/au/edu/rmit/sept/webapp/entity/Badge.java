package au.edu.rmit.sept.webapp.entity;

import java.util.HashMap;

public class Badge {
    private int    badge_id;
    private String badge_name;
    private String badge_description;
    private String badge_image;

    // we want to define sets of flags that the user can
    // flip to then award them the badge
    // we can give a user a badge by checking if every key 
    // in the hashmap is true
    private HashMap<String, Boolean> badge_flags;

    // default constructor 
    public Badge() {}

    public Badge(int badge_id, String badge_name, String badge_description, String badge_image) {
        this.badge_id           = badge_id;
        this.badge_name         = badge_name;
        this.badge_description  = badge_description;
        this.badge_image        = badge_image;
    }

    // GETTERS 
    int getBadgeID() { return this.badge_id; }
    String getBadgeName() { return badge_name; }
    String getBadgeDescription() { return badge_description; }
    String getBadgeImage() { return badge_image; }

    // SETTERS
    void setbadgeID(int badge_id) { this.badge_id = badge_id; }
    void setBadgeName(String badge_name) { this.badge_name = badge_name; }
    void setBadgeDescription(String badge_description) { this.badge_description = badge_description; }
    void setBadgeImage(String badge_image) { this.badge_image = badge_description; }

    void setBadgeFlagCondition(String flagCondition, Boolean setFlag) { badge_flags.put(flagCondition, setFlag); }
    void removeBadgeFlagCondition(String flagCondition) {
        boolean removed = badge_flags.remove(flagCondition);
        if(!removed) { System.out.println("Error: Unable to remove flag (" + flagCondition + ")\n"); }
    }
    
}
