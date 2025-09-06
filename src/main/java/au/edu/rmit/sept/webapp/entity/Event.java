package au.edu.rmit.sept.webapp.entity;

import au.edu.rmit.sept.webapp.entity.User;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int owner_id;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> participants;
    private LocalDateTime date;
    private String location;
    private String category;
    private List<String> keywords;
    private List<String> tags;
    private List<String> images;

    // Default Constructor
    public Event() {}

    // Temp Testing Constructor
    public Event(String title, String description, int owner, LocalDateTime date,
                 String location, String category) {
        this.title = title;
        this.description = description;
        this.owner_id = owner;
        this.date = date;
        this.location = location;
        this.category = category;
    }


    // No ID Constructor
    public Event(String title, String description, int owner, LocalDateTime date,
                 String location, String category, List<String> keywords,
                 List<String> tags, List<String> images) {
        this.title = title;
        this.description = description;
        this.owner_id = owner;
        participants = new ArrayList<User>();
        this.date = date;
        this.location = location;
        this.category = category;
        this.keywords = keywords;
        this.tags = tags;
        this.images = images;
    }

    // Full Constructor
    public Event(int id, String title, String description, int owner, LocalDateTime date,
                 String location, String category, List<String> keywords,
                 List<String> tags, List<String> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner_id = owner;
        participants = new ArrayList<User>();
        this.date = date;
        this.location = location;
        this.category = category;
        this.keywords = keywords;
        this.tags = tags;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwner() {
        return owner_id;
    }

    public void setOwner(int owner) {
        this.owner_id = owner;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(User participant) {
        this.participants.remove(participant);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}