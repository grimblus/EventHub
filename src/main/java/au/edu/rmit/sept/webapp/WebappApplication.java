package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.repository.EventRepo;
import au.edu.rmit.sept.webapp.repository.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
        UserRepo.setUsers();
        EventRepo.setEvents();
	}
}
