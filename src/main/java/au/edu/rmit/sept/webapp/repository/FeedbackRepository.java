package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Feedback;
import java.util.List;

public interface FeedbackRepository {
    boolean add(Feedback feedback);
    List<Feedback> findByUserId(int userId);
    List<Feedback> findByEventId(int eventId);

}