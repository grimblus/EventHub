package au.edu.rmit.sept.webapp.entity;

import java.time.LocalDateTime;

public class Feedback {
    private Integer id;
    private Integer userId;
    private Integer eventId;
    private String  eventTitle;
    private Integer rating;
    private String  comment;
    private LocalDateTime createdAt;

    //Default Constructor
    public Feedback() {
        this.id = 0;
        this.userId = 0;
        this.eventId = 0;
        this.eventTitle = "";
        this.rating = 0;
        this.comment = "";
        this.createdAt = LocalDateTime.MIN;
    }

    //Full Constructor
    public Feedback(Integer id, Integer userId, Integer eventId, String eventTitle, Integer rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}