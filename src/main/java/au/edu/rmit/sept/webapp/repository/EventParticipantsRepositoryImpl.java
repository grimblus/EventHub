package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;

import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;

@Repository
public class EventParticipantsRepositoryImpl implements EventParticipantsRepository {
    final private Supplier<Connection> connectionSupplier;

    public EventParticipantsRepositoryImpl() {
        this.connectionSupplier = () -> {
            try {
                return H2DatabaseConnection.getConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public EventParticipantsRepositoryImpl(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public List<User> getEventParticipants(Integer eventId) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionSupplier.get()) {
            String searchSQL = "SELECT * " +
                    "FROM event_participants ep " +
                    "INNER JOIN users u ON ep.user_id = u.id " +
                    "WHERE ep.event_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, eventId);
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        userList.add(new User(
                                rs.getInt("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("password"),
                                rs.getString("email"),
                                User.permissions.valueOf(rs.getString("permissionLevel").toUpperCase()),
                                rs.getBoolean("banned")));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<Integer> getRSVPList(Integer userId) {
        List<Integer> eventList = new ArrayList<>();
        try (Connection connection = connectionSupplier.get()) {
            String searchSQL = "SELECT * FROM event_participants WHERE user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, userId);
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        eventList.add(rs.getInt("event_id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventList;
    }



    public void addEventParticipant(Integer eventId, Integer userId) {
        try (Connection connection = connectionSupplier.get()) {
            String insertSQL = "INSERT INTO event_participants (event_id, user_id) VALUES (?, ?)";
         
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setInt(1, eventId);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean PrintAttendeeCSV(Integer eventId) {
        System.out.println("Printing event atendeeeeees");
        return true;
    }
}
