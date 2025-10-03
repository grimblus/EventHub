package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.entity.Badge;
import java.util.*;

public interface BadgeService {
    public List<Badge> getBadgesForUser(int userId);
    public void awardBadge(int userId, int badgeId);
}