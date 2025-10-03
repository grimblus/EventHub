package au.edu.rmit.sept.webapp.services;

import java.util.*;

import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.BadgeRepository;
import au.edu.rmit.sept.webapp.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class BadgeServiceImpl {
    private UserRepository userRepo;
    private BadgeRepository badgeRepo;

    public BadgeServiceImpl(BadgeRepository badgeRepo) {
        this.badgeRepo = badgeRepo;
    }

    public List<Badge> getBadgesForUser(int userId) {
        return badgeRepo.getBadgesByUser(userId);
    }

    public void awardBadge(int userId, int badgeId) {
        badgeRepo.assignBadgeToUser(userId, badgeId);
    }
}
