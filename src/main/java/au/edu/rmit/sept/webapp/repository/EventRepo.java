package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.entity.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

public class EventRepo {
    static List<Event> eventList = List.of(
            new Event("Workshop", "Learn Basics", 5, LocalDateTime.of(2025, 9, 1, 10, 0), "Melbourne", "General"),
            new Event("Hackathon", "Coding Challenge", 4, LocalDateTime.of(2025, 10, 10, 9, 0), "Melbourne", "Coding")
    );

    public static void setEvents() {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS events (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "owner_id INT, " +
                    "date TIMESTAMP," +
                    "location VARCHAR(255), " +
                    "category VARCHAR(255))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO events (title, description, owner_id, date, location, category) VALUES (?, ?, ?, ?, ?, ?)";
            for (Event event : eventList) {
                try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                    ps.setString(1, event.getTitle());
                    ps.setString(2, event.getDescription());
                    ps.setInt(3, event.getOwner());
                    ps.setObject(4, event.getDate());
                    ps.setString(5, event.getLocation());
                    ps.setString(6, event.getCategory());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}