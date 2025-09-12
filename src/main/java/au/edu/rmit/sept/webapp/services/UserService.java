package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);
    public void setUsers();
    public List<User> getUsers();
    public User findUserByID(Integer id);
    public User findUserByFirstName(String firstName);
    public User findUserByLastName(String lastName);
    public User findUserByEmail(String email);
}