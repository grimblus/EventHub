package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.entity.Badge;

@Repository
public class BadgeRepositoryImpl implements BadgeRepository {
    private final DataSource dataSource;
    
    // placeholder badges
    static List<Badge> badgeList = List.of(
        new Badge(0, "2 Attends", "Attend 2 Events"),
        new Badge (0, "Create event", "Create an event"),
        new Badge (0, "Payment", "Pay for an event")
    );

    public BadgeRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setBadges() {
        try (Connection connection = this.dataSource.getConnection()) {
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
        try (Connection connection = this.dataSource.getConnection()) {
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
        try (Connection connection = this.dataSource.getConnection()) {
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
        try (Connection connection = this.dataSource.getConnection()) {
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
