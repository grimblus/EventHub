package au.edu.rmit.sept.webapp.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private int id;
    private String title;
    private String description;
    private User owner;
    private List<User> participants;
    private LocalDateTime date;
    private String location;
    private String category;
    private List<String> keywords;
    private List<String> tags;
    private List<String> images;

    // Default Constructor
    public Event() {}

    // Full Constructor
    public Event(int id, String title, String description, User owner, LocalDateTime date,
                 String location, String category, List<String> keywords,
                 List<String> tags, List<String> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
