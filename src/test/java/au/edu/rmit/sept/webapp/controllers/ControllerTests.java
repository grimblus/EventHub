package au.edu.rmit.sept.webapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerTests {
    @Autowired private AccountController accController;
    @Autowired private AdminController adminController;
    @Autowired private CreateController createController;
    @Autowired private EventFeedController eventFeedController;
    @Autowired private GlobalController globalController;
    @Autowired private HomeController homeController;
    @Autowired private LoginController loginController;
    @Autowired private MyEventsController myEventsController;
    @Autowired private MyNotificationsController myNotificationsController;
    @Autowired private PageController pageController;
    @Autowired private PaymentController paymentController;
    @Autowired private RSVPListController rsvpListController;
    @Autowired private SignUpController signUpController;

	@Test
	void contextLoads() throws Exception {
        assertAll("controllers",
            () -> assertThat(accController).isNotNull(),
            () -> assertThat(adminController).isNotNull(),
            () -> assertThat(createController).isNotNull(),
            () -> assertThat(eventFeedController).isNotNull(),
            () -> assertThat(globalController).isNotNull(),
            () -> assertThat(homeController).isNotNull(),
            () -> assertThat(loginController).isNotNull(),
            () -> assertThat(myEventsController).isNotNull(),
            () -> assertThat(myNotificationsController).isNotNull(),
            () -> assertThat(pageController).isNotNull(),
            () -> assertThat(paymentController).isNotNull(),
            () -> assertThat(rsvpListController).isNotNull(),
            () -> assertThat(signUpController).isNotNull()
        );
	}
}

