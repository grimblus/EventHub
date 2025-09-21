package au.edu.rmit.sept.webapp.repository;
import java.util.List;
import au.edu.rmit.sept.webapp.entity.User;

public interface UserRepository {
    public void setUsers();
    public List<User> getUsers();
    public  User findUserByID(Integer id);
    public  User findUserByFirstName(String firstName);
    public  User findUserByLastName(String lastName);
    public  User findUserByEmail(String email);
    public void addUser(User user);
    public boolean toggleBanned(Integer id);
}