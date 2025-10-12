package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Feedback;
import java.util.List;

public interface FeedbackService {
    public boolean submitForEvent(int userId, int eventId, String eventTitle, int rating, String comment);
    public List<Feedback> findByUserId(int userId);
    public List<Feedback> findByEventId(int eventId);
}