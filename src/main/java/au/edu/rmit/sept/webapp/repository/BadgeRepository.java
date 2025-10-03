package au.edu.rmit.sept.webapp.repository;
import java.util.*;
import au.edu.rmit.sept.webapp.entity.Badge;

public interface BadgeRepository {
    public void setBadges();
    public void addBadge(Badge badge);
    public List<Badge> getBadgesByUser(int userId);
    public void assignBadgeToUser(int userId, int badgeId);
}
