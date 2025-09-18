package au.edu.rmit.sept.webapp.entity;

import java.util.ArrayList;

public class User {
    public enum permissions {STUDENT, ORGANISER, ADMIN};

    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private permissions permissionLevel;
    private boolean banned;
    private ArrayList<Event> rsvpList;

    // Default Constructor
    public User() {}

    // No ID Constructor
    public User(String firstName, String lastName, String password, String email, permissions permissionLevel) {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.permissionLevel = permissionLevel;
        this.banned = false;
        this.rsvpList = new ArrayList<>();
    }

    // Full Constructor
    public User(Integer id, String firstName, String lastName, String password, String email, permissions permissionLevel, boolean banned) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.permissionLevel = permissionLevel;
        this.banned = banned;
        this.rsvpList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Event> getRsvpList() {
        return rsvpList;
    }

    public void addRsvp(Event event) {
        this.rsvpList.add(event);
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public permissions getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(permissions permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}