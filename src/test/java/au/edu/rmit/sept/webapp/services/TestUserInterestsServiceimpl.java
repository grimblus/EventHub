package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;
import au.edu.rmit.sept.webapp.repository.UserInterestsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserInterestsServiceimpl {
    @Mock
    private UserInterestsRepository userInterestsRepository;

    @InjectMocks
    private UserInterestsServiceImpl userInterestsService;


    @Test
    void testGetAllUserInterests() {
        HashSet<String> interests = new HashSet<>(Set.of("GENERAL", "CODING"));
        when(userInterestsRepository.getUserInterests(1)).thenReturn( interests);

        HashSet<String> result = userInterestsService.getUserInterests(1);

        assertEquals(interests, result);
    }

    @Test
    void testAddUserInterests() {
        ArrayList<String> interests = new ArrayList<>(List.of("GENERAL", "CODING"));
        userInterestsService.addUserInterests(1,  interests);
        verify(userInterestsRepository).addUserInterests(1, interests);
    }

}
