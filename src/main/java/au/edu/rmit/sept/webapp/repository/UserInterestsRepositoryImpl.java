package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Supplier;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import org.springframework.stereotype.Repository;

@Repository
public class UserInterestsRepositoryImpl implements UserInterestsRepository{
    final private Supplier<Connection> connectionSupplier;

    public UserInterestsRepositoryImpl() {
        this.connectionSupplier = () -> {
            try {
                return H2DatabaseConnection.getConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public UserInterestsRepositoryImpl(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public void setUserInterests() {
        try (Connection connection = connectionSupplier.get()) {
            String createTableSQL = 
            "CREATE TABLE IF NOT EXISTS user_interests (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "interest VARCHAR(255), " +
            "user_id INT, " +
            "FOREIGN KEY (user_id) REFERENCES users(id))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserInterests(int user_id, ArrayList<String> interests) {
        try (Connection connection = connectionSupplier.get()) {
            String deleteSQL = "DELETE FROM user_interests WHERE user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {
                stmt.setInt(1, user_id); // safely sets the value
                stmt.executeUpdate();
            }
            if (interests != null) {
            String insertSQL = "INSERT INTO user_interests (interest, user_id) VALUES (?, ?)";
                for (String interest : interests) {
                    try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {

                        ps.setString(1, interest);
                        ps.setInt(2, user_id);
                        ps.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashSet<String> getUserInterests(int user_id) {
        HashSet<String> interests = new HashSet<String>();
        try (Connection connection = connectionSupplier.get()) {
            String searchSQL = "SELECT * FROM user_interests WHERE user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, user_id);
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String interest =
                                rs.getString("interest");

                        interests.add(interest);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interests;
    }
}
