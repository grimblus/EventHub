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
    public void addUser(User user) {
         repository.addUser(user);
    }
    @Override
    public void setUsers() {
        repository.setUsers();
    }
    public List<User> getUsers() {
        return repository.getUsers();
    }
    public  User findUserByID(Integer id) {
        return repository.findUserByID(id);
    }
    public  User findUserByFirstName(String firstName) {
        return repository.findUserByFirstName(firstName);
    }
    public  User findUserByLastName(String lastName) {
        return repository.findUserByLastName(lastName);
    }
    public  User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }
}