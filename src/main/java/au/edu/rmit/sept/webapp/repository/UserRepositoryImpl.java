package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource;

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
}