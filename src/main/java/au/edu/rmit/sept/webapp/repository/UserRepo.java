package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class UserRepo {
    static List<User> userList = List.of(
        new User("aaron", "password123", "aaron@example.com", 0),
        new User("bingus", "s3cur3pass", "bingus@example.com", 2),
        new User("coolguy", "coolguy123", "coolguy@example.com", 0),
        new User("johndev", "g0d0t0man", "johndev@example.com", 1),
        new User("morgan", "b4yharb0r", "morgan@example.com", 1),
        new User("deez", "n00ts", "deez@example.com", 0)
    );

    public static void setUsers() {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255), " +
                    "password VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "permissions INT)";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO users (username, password, email, permissions) VALUES (?, ?, ?, ?)";
            for (User user : userList) {
                try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getEmail());
                    ps.setInt(4, user.getPermissions());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
