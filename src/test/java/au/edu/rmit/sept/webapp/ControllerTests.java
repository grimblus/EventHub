package au.edu.rmit.sept.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.springframework.boot.test.context.SpringBootTest;

import au.edu.rmit.sept.webapp.controllers.AccountController;
import au.edu.rmit.sept.webapp.controllers.AdminController;
import au.edu.rmit.sept.webapp.controllers.CreateController;
import au.edu.rmit.sept.webapp.controllers.EventFeedController;
import au.edu.rmit.sept.webapp.controllers.GlobalController;
import au.edu.rmit.sept.webapp.controllers.HomeController;
import au.edu.rmit.sept.webapp.controllers.LoginController;
import au.edu.rmit.sept.webapp.controllers.MyEventsController;
import au.edu.rmit.sept.webapp.controllers.MyNotificationsController;
import au.edu.rmit.sept.webapp.controllers.PageController;
import au.edu.rmit.sept.webapp.controllers.PaymentController;
import au.edu.rmit.sept.webapp.controllers.RSVPListController;
import au.edu.rmit.sept.webapp.controllers.SignUpController;

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

