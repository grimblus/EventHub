package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Feedback;
import au.edu.rmit.sept.webapp.entity.User;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final DataSource dataSource;

    public FeedbackRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean add(Feedback f) {
        String sql = "INSERT INTO feedback (user_id, event_id, event_title, rating, comment, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, f.getUserId());
            ps.setInt(2, f.getEventId());
            ps.setString(3, f.getEventTitle());
            ps.setInt(4, f.getRating());
            ps.setString(5, f.getComment());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Feedback> findByUserId(int userId) {
        String sql = "SELECT id, user_id, event_id, event_title, rating, comment, created_at " +
                     "FROM feedback WHERE user_id = ? ORDER BY created_at DESC";
        List<Feedback> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback f = new Feedback();
                    f.setId(rs.getInt("id"));
                    f.setUserId(rs.getInt("user_id"));
                    f.setEventId(rs.getInt("event_id"));
                    f.setEventTitle(rs.getString("event_title"));
                    f.setRating(rs.getInt("rating"));
                    f.setComment(rs.getString("comment"));

                    Timestamp ts = rs.getTimestamp("created_at");
                    f.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);

                    list.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Feedback> findByEventId(int eventId) {
        String sql = "SELECT f.id, f.user_id, f.event_id, f.event_title, f.rating, f.comment, f.created_at, u.firstname, u.lastname, u.email " +
        "FROM feedback f JOIN users u on u.id = f.user_id WHERE f.event_id = ? ORDER BY created_at DESC";
        List<Feedback> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback f = new Feedback();
                    f.setId(rs.getInt("id"));
                    f.setUserId(rs.getInt("user_id"));
                    f.setEventId(rs.getInt("event_id"));
                    f.setEventTitle(rs.getString("event_title"));
                    f.setRating(rs.getInt("rating"));
                    f.setComment(rs.getString("comment"));

                    Timestamp ts = rs.getTimestamp("created_at");
                    f.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
                    
                    User user = new User();
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    
                    f.setUser(user);
                    list.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}