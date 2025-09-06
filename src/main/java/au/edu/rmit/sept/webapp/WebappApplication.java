package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.EventRepo;
import au.edu.rmit.sept.webapp.repository.UserRepo;
import au.edu.rmit.sept.webapp.services.UserService;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);

		
	}

	@Bean
    public CommandLineRunner dataLoader(UserService userService, UserRepo userRepo) { //initialises database
        return args -> {
			if (userRepo.count() == 0) {
				List<User> userList = List.of(
					new User("Aaron", "Tran", "password123", "aaron@example.com", 0),
					new User("Bingus", "Bongus", "s3cur3pass", "bingus@example.com", 2),
					new User("Coolguy", "Joe", "coolguy123", "coolguy@example.com", 0)
				);

				for (User user : userList) {
					userService.addUser(user);  
				}
			}

        };
    }
}
