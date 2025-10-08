package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.repository.BadgeRepository;
import au.edu.rmit.sept.webapp.repository.EventParticipantsRepository;
import au.edu.rmit.sept.webapp.repository.EventRepository;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestBadgeServiceImpl {

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private EventParticipantsRepository eventParticipantsRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BadgeServiceImpl badgeService;

    @Test
    void testGetBadgesByUser() {
        int userId = 1;
        List<Badge> badges = List.of(new Badge());
        when(badgeRepository.getBadgesByUser(userId)).thenReturn(badges);

        List<Badge> result = badgeService.getBadgesByUser(userId);

        assertEquals(badges, result);
        verify(badgeRepository).getBadgesByUser(userId);
    }

    @Test
    void testGetAllBadges() {
        List<Badge> badges = List.of(new Badge());
        when(badgeRepository.getAllBadges()).thenReturn(badges);

        List<Badge> result = badgeService.getAllBadges();

        assertEquals(badges, result);
        verify(badgeRepository).getAllBadges();
    }

    @Test
    void testAssign2AttendBadge() {
        int userId = 1;
        Badge badge = new Badge(1, "2 Attends", "Attend 2 events");

        when(eventParticipantsRepository.getRSVPList(userId)).thenReturn(List.of(1, 2));
        when(badgeRepository.getBadgesByUser(userId)).thenReturn(List.of());
        when(badgeRepository.getAllBadges()).thenReturn(List.of(badge));

        badgeService.assign2AttendBadge(userId);

        verify(badgeRepository).assignBadgeToUser(userId, 1);
    }

    @Test
    void testAssignCreate() {
        int userId = 1;
        Event event = new Event();
        Badge badge = new Badge(2, "Create Event", "Create an event");

        when(eventRepository.getEventsByOwnerId(userId)).thenReturn(List.of(event));
        when(badgeRepository.getBadgesByUser(userId)).thenReturn(List.of());
        when(badgeRepository.getAllBadges()).thenReturn(List.of(badge));

        badgeService.assignCreate(userId);

        verify(badgeRepository).assignBadgeToUser(userId, 2);
    }

    @Test
    void testAssignPaymentBadge() {
        int userId = 1;
        Payment payment = new Payment();
        Badge badge = new Badge(3, "Payment", "Pay for an event");

        when(paymentRepository.GetUserPayments(userId)).thenReturn(List.of(payment));
        when(badgeRepository.getBadgesByUser(userId)).thenReturn(List.of());
        when(badgeRepository.getAllBadges()).thenReturn(List.of(badge));

        badgeService.assignPaymentBadge(userId);

        verify(badgeRepository).assignBadgeToUser(userId, 3);
    }

    @Test
    void testAlreadyHasBadge() {
        int userId = 1;
        Badge badge = new Badge(3, "Payment", "Pay for an event");
        when(badgeRepository.getBadgesByUser(userId)).thenReturn(List.of(badge));

        boolean result = badgeService.alreadyHasBadge(userId, "Payment");

        assertTrue(result);
    }
}
