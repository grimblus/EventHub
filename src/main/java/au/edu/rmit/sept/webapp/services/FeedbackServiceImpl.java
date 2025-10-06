package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Feedback;
import au.edu.rmit.sept.webapp.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repo;

    public FeedbackServiceImpl(FeedbackRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean submitForEvent(int userId, int eventId, String eventTitle, int rating, String comment) {
        if (rating < 1 || rating > 5) return false;
        if (comment == null || comment.isBlank()) return false;

        Feedback f = new Feedback();
        f.setUserId(userId);
        f.setEventId(eventId);
        f.setEventTitle(eventTitle);
        f.setRating(rating);
        f.setComment(comment.trim());

        return repo.add(f);
    }

    @Override
    public List<Feedback> findByUserId(int userId) {
        return repo.findByUserId(userId);
    }
}