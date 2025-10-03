package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Supplier;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.entity.User;

@Repository
public class BadgeRepositoryImpl implements BadgeRepository {
    final private Supplier<Connection> connectionSupplier;

    public BadgeRepositoryImpl() {
        this.connectionSupplier = () -> {
            try {
                return H2DatabaseConnection.getConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public BadgeRepositoryImpl(Supplier<Connection> connectinSupplier) {
        this.connectionSupplier = connectinSupplier;
    }

    public void setBadges() {
        try (Connection connection = connectionSupplier.get()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS badges (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "description VARCHAR(500)";
            connection.prepareStatement(createTableSQL).executeUpdate();

            String joinTableSQL = "CREATE TABLE IF NOT EXISTS user_badges (" +
                    "user_id INT, " +
                    "badge_id INT, " +
                    "PRIMARY KEY (user_id, badge_id))";
            connection.prepareStatement(joinTableSQL).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void addBadge(Badge badge) {
        try (Connection connection = connectionSupplier.get()) {
            String sql = "INSERT INTO badges (name, description, requirement) VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, badge.getBadgeName());
                ps.setString(2, badge.getBadgeDescription());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Badge> getBadgesByUser(int userId) {
        List<Badge> badges = new ArrayList<>();
        try (Connection connection = connectionSupplier.get()) {
            String sql = "SELECT b.id, b.name, b.description, b.image " +
                         "FROM badges b " +
                         "JOIN user_badges ub ON b.id = ub.badge_id " +
                         "WHERE ub.user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        badges.add(new Badge(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description")
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return badges;
    }

    public void assignBadgeToUser(int userId, int badgeId) {
        try (Connection connection = connectionSupplier.get()) {
            String sql = "INSERT INTO user_badges (user_id, badge_id) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setInt(2, badgeId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
