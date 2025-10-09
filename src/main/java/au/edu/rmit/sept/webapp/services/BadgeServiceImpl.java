package au.edu.rmit.sept.webapp.services;

import java.util.*;

import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.repository.BadgeRepository;
import au.edu.rmit.sept.webapp.repository.EventParticipantsRepository;
import au.edu.rmit.sept.webapp.repository.EventRepository;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;

import org.springframework.stereotype.Service;

@Service
public class BadgeServiceImpl implements BadgeService {
    private BadgeRepository badgeRepo;
    private PaymentRepository paymentRepo;
    private EventParticipantsRepository eventParticipantRepo;
    private EventRepository eventRepo;

    // constant for the assign2AttendBadge to check for
    private static final int REQUIREDAMOUNT = 2;


    public BadgeServiceImpl(BadgeRepository badgeRepo, PaymentRepository paymentRepo, EventParticipantsRepository eventParticipantRepo, EventRepository eventRepo) {
        this.badgeRepo = badgeRepo;
        this.paymentRepo = paymentRepo;
        this.eventParticipantRepo = eventParticipantRepo;
        this.eventRepo = eventRepo;
    }

    public List<Badge> getBadgesByUser(int userId) {
        // List<Badge> badges = badgeRepository.getBadgesForUser(int userId);
        // System.out.print("Badges for user: " + userId + ": " + badges);
        return badgeRepo.getBadgesByUser(userId);
    }

    public List<Badge> getAllBadges() {
        return badgeRepo.getAllBadges();
    }

    public void assign2AttendBadge(int userId) {
        List<Integer> rsvps = eventParticipantRepo.getRSVPList(userId);

        // If has 2 or more rsvps, award the badge
        if (rsvps.size() >= REQUIREDAMOUNT) {
            List<Badge> allBadges = badgeRepo.getAllBadges();
            Optional<Badge> attendBadge = allBadges.stream()
                    .filter(b -> b.getBadgeName().equalsIgnoreCase("2 Attends"))
                    .findFirst();

            if (attendBadge.isPresent()) {
                badgeRepo.assignBadgeToUser(userId, attendBadge.get().getBadgeID());
            }
        }
    }

    public void assignCreate(int userId) {
        List<Event> created = eventRepo.getEventsByOwnerId(userId);

        // If has at least 1 created, award badge
        if (!created.isEmpty()) {
            List<Badge> allBadges = badgeRepo.getAllBadges();
            Optional<Badge> createBadge = allBadges.stream()
                    .filter(b -> b.getBadgeName().equalsIgnoreCase("Create Event"))
                    .findFirst();

            if (createBadge.isPresent()) {
                badgeRepo.assignBadgeToUser(userId, createBadge.get().getBadgeID());
            }
        }
    }

    public void assignPaymentBadge(int userId) {
        List<Payment> payments = paymentRepo.GetUserPayments(userId);

        // If has at least 1 payment, award the badge
        if (!payments.isEmpty()) {
            List<Badge> allBadges = badgeRepo.getAllBadges();
            Optional<Badge> paymentBadge = allBadges.stream()
                    .filter(b -> b.getBadgeName().equalsIgnoreCase("Payment"))
                    .findFirst();

            if (paymentBadge.isPresent()) {
                badgeRepo.assignBadgeToUser(userId, paymentBadge.get().getBadgeID());
            }
        }
    }
}
