package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    //adds user to db
    public void addUser(User user) {
         repository.addUser(user);
    }


    @Override
    //gets all users
    public List<User> getUsers() {
        return repository.getUsers();
    }

    //finds user using id
    public User findUserByID(Integer id) {
        return repository.findUserByID(id);
    }

    //finds user using firstname
    public User findUserByFirstName(String firstName) {
        return repository.findUserByFirstName(firstName);
    }

    //finds user using last name
    public User findUserByLastName(String lastName) {
        return repository.findUserByLastName(lastName);
    }

    //finds user using email
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    //sets the ban state of a user
    public boolean toggleBanned(Integer id) {return repository.toggleBanned(id);}
}