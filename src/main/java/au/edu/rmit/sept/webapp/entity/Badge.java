package au.edu.rmit.sept.webapp.entity;

import java.util.HashMap;
import java.util.Objects;

public class Badge {
    private int    badge_id;
    private String badge_name;
    private String badge_description;
    // private String badge_image;

    // we want to define sets of flags that the user can
    // flip to then award them the badge
    // we can give a user a badge by checking if every key 
    // in the hashmap is true
    // private HashMap<String, Boolean> badge_flags;

    // default constructor 
    public Badge() {
        badge_id           = 0;
        badge_name         = "";
        badge_description  = "";
        // badge_image        = "";
        // badge_flags       = new HashMap<>();
    }

    // no ID constructor
    public Badge(int badge_id, String badge_name, String badge_description
    // , String badge_image
    ) {
        this.badge_id           = badge_id;
        this.badge_name         = badge_name;
        this.badge_description  = badge_description;
        // this.badge_image        = badge_image;
        // this.badge_flags        = new HashMap<>();
    }

    // GETTERS 
    public int getBadgeID() { return this.badge_id; }
    public String getBadgeName() { return badge_name; }
    public String getBadgeDescription() { return badge_description; }
    // public String getBadgeImage() { return badge_image; }
    // public HashMap<String, Boolean> getBadgeFlags() { return this.badge_flags; }

    // SETTERS
    public void setBadgeID(int badge_id) { this.badge_id = badge_id; }
    public void setBadgeName(String badge_name) { this.badge_name = badge_name; }
    public void setBadgeDescription(String badge_description) { this.badge_description = badge_description; }
    // public void setBadgeImage(String badge_image) { this.badge_image = badge_image; }
    // public void setBadgeFlags(HashMap<String, Boolean> badge_flags) { this.badge_flags = badge_flags; }

    // public void setBadgeFlagCondition(String flagCondition, Boolean setFlag) { badge_flags.put(flagCondition, setFlag); }
    // public void removeBadgeFlagCondition(String flagCondition) {
    //     boolean removed = (badge_flags.remove(flagCondition) != null);
    //     if(!removed) { System.out.println("Error: Unable to remove flag (" + flagCondition + ")\n"); }
    // }
    
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Badge)) return false;
        Badge badge = (Badge) o;
        return badge_id == badge.badge_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(badge_id);
    }
}
