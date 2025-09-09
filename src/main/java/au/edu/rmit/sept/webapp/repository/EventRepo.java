package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventRepo {
    static List<Event> eventList = List.of(
            new Event("Workshop", "Learn Basics", 5, LocalDateTime.of(2025, 9, 1, 10, 0), "Melbourne", "General", List.of("java", "workshop"), List.of("education", "beginner"), List.of("temp.png", "temp.png")),
            new Event("Hackathon", "Coding Challenge", 4, LocalDateTime.of(2025, 10, 10, 9, 0), "Melbourne", "Coding", List.of("coding", "challenge"), List.of("competition", "teamwork"), List.of("temp.png", "temp.png"))
    );

    public static List<String> stringList(String list) {
        List<String> finalList = new ArrayList<>();
        for (String s : list.split(","))
            finalList.add(s);
        return finalList;
    }

    public static void setEvents() {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS events (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "owner_id INT, " +
                    "date TIMESTAMP," +
                    "location VARCHAR(255), " +
                    "category VARCHAR(255)," +
                    "keywords VARCHAR(1000)," +
                    "tags VARCHAR(1000)," +
                    "images VARCHAR(1000)," +
                    "FOREIGN KEY(owner_id) REFERENCES users(id))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO events (title, description, owner_id, date, location, category, keywords, tags, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (Event event : eventList) {
                try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                    ps.setString(1, event.getTitle());
                    ps.setString(2, event.getDescription());
                    ps.setInt(3, event.getOwner());
                    ps.setTimestamp(4, Timestamp.valueOf(event.getDate()));
                    ps.setString(5, event.getLocation());
                    ps.setString(6, event.getCategory());
                    ps.setString(7, String.join(",", event.getKeywords()));
                    ps.setString(8, String.join(",", event.getTags()));
                    ps.setString(9, String.join(",", event.getImages()));
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS event_participants (" +
                    "    event_id INT NOT NULL," +
                    "    user_id INT NOT NULL," +
                    "    PRIMARY KEY (event_id, user_id)," +
                    "    FOREIGN KEY (event_id) REFERENCES events(id)," +
                    "    FOREIGN KEY (user_id) REFERENCES users(id))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO event_participants (event_id, user_id) VALUES (?, ?)";
            for (Event event: eventList) {
                for (User user : event.getParticipants()) {
                    try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                        ps.setInt(1, event.getId());
                        ps.setInt(2, user.getId());
                        ps.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addEvent(Event event) {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO events (title, description, owner_id, date, location, category, keywords, tags, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, event.getTitle());
                ps.setString(2, event.getDescription());
                ps.setInt(3, event.getOwner());
                ps.setTimestamp(4, Timestamp.valueOf(event.getDate()));
                ps.setString(5, event.getLocation());
                ps.setString(6, event.getCategory());
                ps.setString(7, String.join(",", event.getKeywords()));
                ps.setString(8, String.join(",", event.getTags()));
                ps.setString(9, String.join(",", event.getImages()));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String searchSQL = "SELECT * FROM events";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Event event = new Event(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getInt("owner_id"),
                                rs.getTimestamp("date").toLocalDateTime(),
                                rs.getString("location"),
                                rs.getString("category"),
                                stringList(rs.getString("keywords")),
                                stringList(rs.getString("tags")),
                                stringList(rs.getString("images"))
                        );
                        events.add(event);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}