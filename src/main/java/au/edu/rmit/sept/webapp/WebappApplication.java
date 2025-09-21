package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.repository.EventRepository;
import au.edu.rmit.sept.webapp.repository.EventRepositoryImpl;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;
import au.edu.rmit.sept.webapp.repository.PaymentRepositoryImpl;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import au.edu.rmit.sept.webapp.repository.UserRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
		UserRepository userRepo = new UserRepositoryImpl();
        EventRepository eventRepo = new EventRepositoryImpl();
		PaymentRepository paymentRepo = new PaymentRepositoryImpl();
        userRepo.setUsers();
        eventRepo.setEvents();
		paymentRepo.setPayments();
	}
}
