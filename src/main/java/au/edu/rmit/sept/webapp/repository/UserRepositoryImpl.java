package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource;

    public List<String> stringList(String list) {
        List<String> finalList = new ArrayList<>();
        for (String s : list.split(","))
            finalList.add(s);
        return finalList;
    }

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addUser(User user) {
        try (Connection connection = this.dataSource.getConnection()) {
            String insertSQL = "INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPermissionLevel().name());
                ps.setBoolean(6, user.isBanned());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM users";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User user = new User(
                                rs.getInt("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("password"),
                                rs.getString("email"),
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase()),
                                rs.getBoolean("banned")
                        );
                        users.add(user);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public  User findUserByID(Integer id) {
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, id);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("password"),
                            rs.getString("email"),
                            User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase()),
                            rs.getBoolean("banned")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  User findUserByFirstName(String firstName) {
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM users WHERE firstName = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setString(1, firstName);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("password"),
                                rs.getString("email"),
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase()),
                                rs.getBoolean("banned")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  User findUserByLastName(String lastName) {
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM users WHERE lastName = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setString(1, lastName);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("password"),
                                rs.getString("email"),
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase()),
                                rs.getBoolean("banned")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUserByEmail(String email) {
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setString(1, email);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("password"),
                                rs.getString("email"),
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase()),
                                rs.getBoolean("banned")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean toggleBanned(Integer id) {
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT banned FROM users WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, id);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        boolean currentBanned = rs.getBoolean("banned");
                        boolean newBanned = !currentBanned;

                        String updateSQL = "UPDATE users SET banned = ? WHERE id = ?";
                        try (PreparedStatement ps2 = connection.prepareStatement(updateSQL)) {
                            ps2.setBoolean(1, newBanned);
                            ps2.setInt(2, id);
                            int rows = ps2.executeUpdate(); //used by tests 
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveEvent(int user_id, int event_id) {
        try (Connection connection = this.dataSource.getConnection()) {
            String insertSQL = "INSERT INTO saved_events (user_id, event_id) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setInt(1, user_id);
                ps.setInt(2, event_id);
                ps.executeUpdate();
            }
        } catch (SQLIntegrityConstraintViolationException exception) {
            System.out.println("Already saved this event.");
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Event> getSavedEvents(int user_id) {
        List<Event> savedEvents = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * " +
                    "FROM saved_events e " +
                    "INNER JOIN events ev ON e.event_id = ev.id " +
                    "WHERE e.user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, user_id);
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        savedEvents.add(new Event(
                            rs.getInt("event_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("ownerID"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getString("location"),
                            rs.getString("ticketPrice"),
                            rs.getString("category"),
                            stringList(rs.getString("keywords")),
                            stringList(rs.getString("tags")),
                            stringList(rs.getString("images"))
                    ));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedEvents;
    }
    public void unsaveEvent(int user_id, int event_id) {
          try (Connection connection = this.dataSource.getConnection()) {
            String deleteSQL = "DELETE FROM saved_events WHERE user_id = ? AND event_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
                ps.setInt(1, user_id);
                ps.setInt(2, event_id);
            
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}