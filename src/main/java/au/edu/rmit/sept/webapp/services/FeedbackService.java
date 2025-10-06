package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Feedback;
import au.edu.rmit.sept.webapp.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackRepository repo;

    public FeedbackService(FeedbackRepository repo) {
        this.repo = repo;
    }

    public boolean submit(int userId, int rating, String comment) {
        if (rating < 1 || rating > 5) return false;
        if (comment == null || comment.isBlank()) return false;

        Feedback f = new Feedback();
        f.setUserId(userId);
        f.setRating(rating);
        f.setComment(comment.trim());
        return repo.add(f);
    }
}