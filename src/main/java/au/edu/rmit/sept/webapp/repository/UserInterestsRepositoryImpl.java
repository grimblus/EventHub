package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.function.Supplier;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;

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
}
