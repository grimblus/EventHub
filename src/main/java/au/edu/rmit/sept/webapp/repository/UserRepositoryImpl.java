package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    static List<User> userList = List.of(
            new User("Aaron", "Tran", "password123", "aaron@example.com", User.permissions.STUDENT),
            new User("Bingus", "Bongus", "s3cur3pass", "bingus@example.com", User.permissions.ADMIN),
            new User("Coolguy", "Joe", "coolguy123", "coolguy@example.com", User.permissions.STUDENT),
            new User("Johndev", "Programmer", "g0d0t0man", "johndev@example.com", User.permissions.ORGANISER),
            new User("Dexter", "Morgan", "b4yharb0r", "morgan@example.com", User.permissions.STUDENT),
            new User("Deez", "Noots", "g0tt3m", "deez@example.com", User.permissions.ORGANISER)
    );

    public void setUsers() {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "firstName VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "password VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "permissionLevel VARCHAR(255))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO users (firstName, lastName, password, email, permissionLevel) VALUES (?, ?, ?, ?, ?)";
            for (User user : userList) {
                try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                    ps.setString(1, user.getFirstName());
                    ps.setString(2, user.getLastName());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getEmail());
                    ps.setString(5, user.getPermissionLevel().name());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO users (firstName, lastName, password, email, permissionLevel) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPermissionLevel().name());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = H2DatabaseConnection.getConnection()) {
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
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase())
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
        try (Connection connection = H2DatabaseConnection.getConnection()) {
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
                            User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase())
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
        try (Connection connection = H2DatabaseConnection.getConnection()) {
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
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase())
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
        try (Connection connection = H2DatabaseConnection.getConnection()) {
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
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase())
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  User findUserByEmail(String email) {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
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
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase())
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}