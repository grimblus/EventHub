package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;

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

    public List<Integer> getEventParticipants(Integer eventId) {
        List<Integer> userList = new ArrayList<>();
        try (Connection connection = connectionSupplier.get()) {
            String searchSQL = "SELECT * FROM event_participants WHERE event_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, eventId);
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        userList.add(rs.getInt("user_id"));
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
    
    public boolean PrintAttendeeCSV(Integer eventID) {
        System.out.println("Printing event atendeeeeees");
        return true;
    }
}
