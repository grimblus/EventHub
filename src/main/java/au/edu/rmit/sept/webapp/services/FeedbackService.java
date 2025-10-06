package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Feedback;
import java.util.List;

public interface FeedbackService {
    public boolean submit(int userId, int rating, String comment);
    public List<Feedback> findByUserId(int userId);
}