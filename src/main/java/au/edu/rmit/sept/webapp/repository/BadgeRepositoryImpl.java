package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.entity.Badge;
import au.edu.rmit.sept.webapp.entity.User;

@Repository
public class BadgeRepositoryImpl implements BadgeRepository {
    final private Supplier<Connection> connectionSupplier;
    
    // placeholder badges
    static List<Badge> badgeList = List.of(
        new Badge(0, "2 Attends", "Attend 2 Events"),
        new Badge (0, "Create event", "Create an event"),
        // new Badge (0, "Save event", "Save an event"),
        new Badge (0, "Payment", "Pay for an event")
    );

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
                    "description VARCHAR(500))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO badges (name, description) VALUES (?, ?)";
            for (Badge badge : badgeList) {
                try (PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, badge.getBadgeName());
                    ps.setString(2, badge.getBadgeDescription());
                    ps.executeUpdate();

                    try(ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            badge.setBadgeID(rs.getInt(1));
                        }
                    }
                }
            }

            String joinTableSQL = "CREATE TABLE IF NOT EXISTS user_badges (" +
                    "user_id INT, " +
                    "badge_id INT, " +
                    "PRIMARY KEY (user_id, badge_id))";
            PreparedStatement createJoinStatement = connection.prepareStatement(joinTableSQL);
            createJoinStatement.executeUpdate();

            String insertJoin = "INSERT INTO user_badges (user_id, badge_id) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(insertJoin)) {
                    ps.setInt(1, 4);
                    ps.setInt(2, badgeList.get(1).getBadgeID());
                    ps.executeUpdate();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Badge> getAllBadges() {
        List<Badge> badges = new ArrayList<>();
        try (Connection connection = connectionSupplier.get()) {
            String sql = "SELECT * FROM badges";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public List<Badge> getBadgesByUser(int userId) {
        List<Badge> badges = new ArrayList<>();
        try (Connection connection = connectionSupplier.get()) {
            String sql = "SELECT b.id, b.name, b.description " +
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
