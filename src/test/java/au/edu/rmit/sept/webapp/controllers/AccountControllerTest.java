package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.BadgeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    private static final String URL = "/account";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BadgeService badgeService;

    @Test
    void accountDisplaysName() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT);

        MvcResult result = mvc.perform(get(URL)
                .sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", testUser))
                .andReturn();

        String pageContent = result.getResponse().getContentAsString();

        assertTrue(pageContent.contains("Hello, Mark Grayson"));
    }

    @Test
    void accountRedirectsIfNotLoggedIn() throws Exception {

        mvc.perform(get(URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

    @Test
    void accountChecksAllBadges() throws Exception {
        User testUser = new User("Mark", "Grayson", "IHateOmniman34@", "invincicool@email.com", User.permissions.STUDENT);
        testUser.setId(4);

        Badge badge1 = new Badge(1, "2 Attends", "Attend 2 Events");
        Badge badge2 = new Badge(2, "Payment", "Pay for an event");
        List<Badge> allBadges = List.of(badge1, badge2);
        List<Badge> userBadges = List.of(badge2);

        when(badgeService.getAllBadges()).thenReturn(allBadges);
        when(badgeService.getBadgesByUser(4)).thenReturn(userBadges);

        mvc.perform(get(URL).sessionAttr("user", testUser))
                .andExpect(status().isOk())
                .andExpect(view().name("account/index"))
                .andExpect(model().attributeExists("user", "allBadges", "userBadges"))
                .andReturn();

        verify(badgeService, times(1)).assignPaymentBadge(4);
        verify(badgeService, times(1)).assign2AttendBadge(4);
        verify(badgeService, times(1)).assignCreate(4);

        verify(badgeService, times(1)).getAllBadges();
        verify(badgeService, times(1)).getBadgesByUser(4);
    }
 

}
