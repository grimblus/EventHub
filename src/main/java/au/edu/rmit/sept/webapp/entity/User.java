package au.edu.rmit.sept.webapp.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

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
    private HashSet<String> interests;
    // Default Constructor
    public User() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.email = "";
        this.permissionLevel = permissions.STUDENT;
        this.banned = false;
        this.rsvpList = new ArrayList<>();
        this.interests = new HashSet<String>();
    }

    // No ID Constructor
    public User(String firstName, String lastName, String password, String email, permissions permissionLevel) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.permissionLevel = permissionLevel;
        this.banned = false;
        this.rsvpList = new ArrayList<>();
        this.interests = new HashSet<String>();
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
        this.rsvpList = new ArrayList<Event>();
        this.interests = new HashSet<String>();
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

    public void setRsvpList(ArrayList<Event> rsvpList) {
        this.rsvpList = rsvpList;
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

    public HashSet<String> getInterests() {
        return this.interests;
    }

    public void addInterest(String interest) {
        this.interests.add(interest);
    }
}