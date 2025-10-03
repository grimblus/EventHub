package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.entity.Badge;
import java.util.*;

public interface BadgeService {
    public List<Badge> getBadgesByUser(int userId);
    public List<Badge> getAllBadges();
    public void awardBadge(int userId, int badgeId);
}