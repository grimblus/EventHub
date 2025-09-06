package au.edu.rmit.sept.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepo;

@Service
public class UserService {
    
    @Autowired
    UserRepo repo;


    public List<User> getUsers() {
        return repo.findAll();
    }

    public void addUser(User user) {
        repo.save(user);
    }
}
