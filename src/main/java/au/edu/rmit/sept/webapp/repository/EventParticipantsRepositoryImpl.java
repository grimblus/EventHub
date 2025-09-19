package au.edu.rmit.sept.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;

@Repository
public class EventParticipantsRepositoryImpl implements EventParticipantsRepository {

    public void addEventParticipant(Integer eventId, Integer userId) {
        
            try (Connection connection = H2DatabaseConnection.getConnection()) {
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
    
}
