package au.edu.rmit.sept.webapp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String title;
    private String description;
    private int ownerID;
    private List<User> participants;
    @DateTimeFormat(pattern = "yyyy-MM-dd'TT'HH:mm")
    private LocalDateTime date;
    private String location;
    private String category;
    private String ticketPrice;
    private List<String> keywords;
    private List<String> tags;
    private List<String> images;
    // Default Constructor
    public Event() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.ownerID = 0;
        this.participants = new ArrayList<User>();
        this.date = LocalDateTime.MIN;
        this.location = "";
        this.ticketPrice = "";
        this.category = "";
        this.keywords = new ArrayList<String>();
        this.tags = new ArrayList<String>();
        this.images = new ArrayList<String>();
    }

    // No ID Constructor
    public Event(String title, String description, int ownerID, LocalDateTime date,
                 String location, String ticketPrice, String category, List<String> keywords,
                 List<String> tags, List<String> images) {
        this.id = 0;
        this.title = title;
        this.description = description;
        this.ownerID = ownerID;
        participants = new ArrayList<User>();
        this.date = date;
        this.location = location;
        this.ticketPrice = ticketPrice;
        this.category = category;
        this.keywords = keywords;
        this.tags = tags;
        this.images = images;
    }

    // Full Constructor
    public Event(int id, String title, String description, int ownerID, LocalDateTime date,
                 String location, String ticketPrice, String category, List<String> keywords,
                 List<String> tags, List<String> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerID = ownerID;
        participants = new ArrayList<User>();
        this.date = date;
        this.location = location;
        this.ticketPrice = ticketPrice;
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

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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

    public void setDate(LocalDateTime date) { this.date = date; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
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

    public void addKeyword(String keyword) {
        this.keywords.add(keyword);
    }

    public void removeKeyword(String keyword) {
        this.keywords.remove(keyword);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        this.images.add(image);
    }

    public void removeImage(String image) {
        this.images.remove(image);
    }
}