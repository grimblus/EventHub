package au.edu.rmit.sept.webapp.repository;
import java.util.List;


import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;

public interface UserRepository {
    public List<User> getUsers();
    public  User findUserByID(Integer id);
    public  User findUserByFirstName(String firstName);
    public  User findUserByLastName(String lastName);
    public  User findUserByEmail(String email);
    public User addUser(User user);
    public boolean toggleBanned(Integer id);
    public void saveEvent(int user_id, int event_id);
    public List<Event> getSavedEvents(int user_id);
    public void unsaveEvent(int user_id, int event_id);
}