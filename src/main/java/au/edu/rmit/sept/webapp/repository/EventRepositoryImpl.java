package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.entity.User;
import jakarta.validation.constraints.Null;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class EventRepositoryImpl implements EventRepository {
    static List<Event> eventList = List.of(
            new Event("Workshop", "Learn Basics", 5, LocalDateTime.of(2025, 9, 1, 10, 0), "Melbourne", "General", List.of("java", "workshop"), List.of("education", "beginner"), List.of("temp.png", "temp.png")),
            new Event("Hackathon", "Coding Challenge", 4, LocalDateTime.of(2025, 10, 10, 9, 0), "Melbourne", "Coding", List.of("coding", "challenge"), List.of("competition", "teamwork"), List.of("temp.png", "temp.png"))
    );

    public List<String> stringList(String list) {
        List<String> finalList = new ArrayList<>();
        for (String s : list.split(","))
            finalList.add(s);
        return finalList;
    }

    public void setEvents() {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS events (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "ownerID INT, " +
                    "date TIMESTAMP," +
                    "location VARCHAR(255), " +
                    "category VARCHAR(255)," +
                    "keywords VARCHAR(1000)," +
                    "tags VARCHAR(1000)," +
                    "images VARCHAR(1000)," +
                    "FOREIGN KEY(ownerID) REFERENCES users(id))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertSQL = "INSERT INTO events (title, description, ownerID, date, location, category, keywords, tags, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (Event event : eventList) {
                try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                    ps.setString(1, event.getTitle());
                    ps.setString(2, event.getDescription());
                    ps.setInt(3, event.getOwnerID());
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

    public void addEvent(Event event) {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO events (title, description, ownerID, date, location, category, keywords, tags, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, event.getTitle());
                ps.setString(2, event.getDescription());
                ps.setInt(3, event.getOwnerID());
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

    public List<Event> getEvents() {
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
                                rs.getInt("ownerID"),
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

    // returns a single event (used for payment page, can be used for event details?)
    // we want to use id as well to stop pulling duplicates, but i dont think the database has them?
    public Event getEvent(int id, String name) {
        Event event = null; // start with a blank event
        StringBuilder searchSQL = new StringBuilder("SELECT * FROM events WHERE id = ? AND LOWER(title) = ?");

        
        try( Connection connection = H2DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(searchSQL.toString())) {
                ps.setInt(1, id);
                ps.setString(2, name.toLowerCase());

                try (ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        event = new Event(
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
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }

    public List<Event> getEventsByFilter(String name, List<String> tags, String location, String category, List<String> keywords) {
        StringBuilder searchSQL = new StringBuilder("SELECT * FROM events WHERE 1=1");
        List<String> parameters = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            searchSQL.append(" AND LOWER(title) LIKE ?");
            parameters.add("%" + name + "%");
        }
        if (tags != null && !tags.isEmpty()) {
            StringBuilder addSQL = new StringBuilder("AND (");
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0) { addSQL.append(" OR "); }
                addSQL.append("LOWER(tags) LIKE ?");
                parameters.add("%" + tags.get(i) + "%");
            }
            addSQL.append(")");
            searchSQL.append(addSQL);
        }
        if (location != null && !location.isEmpty()) {
            searchSQL.append(" AND LOWER(location) LIKE ?");
            parameters.add("%" + location + "%");
        }
        if (category != null && !category.isEmpty()) {
            searchSQL.append(" AND LOWER(category) LIKE ?");
            parameters.add("%" + category + "%");
        }
        if (keywords != null && !keywords.isEmpty()) {
            StringBuilder addSQL = new StringBuilder("AND (");
            for (int i = 0; i < keywords.size(); i++) {
                if (i > 0) { addSQL.append(" OR "); }
                addSQL.append("LOWER(keywords) LIKE ?");
                parameters.add("%" + keywords.get(i) + "%");
            }
            addSQL.append(")");
            searchSQL.append(addSQL);
        }

        List<Event> events = new ArrayList<>();
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(searchSQL.toString())) {
                // Bind parameters
                for (int i = 0; i < parameters.size(); i++) {
                    ps.setString(i + 1, parameters.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Event event = new Event(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getInt("ownerID"),
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

    public List<String> getTags() {
        Set<String> tags = new  HashSet<String>();
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String searchSQL = "SELECT tags FROM events";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        tags.addAll(Arrays.asList(rs.getString("tags").split(",")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(tags);
    }

    public List<String> getKeywords() {
        Set<String> keywords = new  HashSet<String>();
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String searchSQL = "SELECT keywords FROM events";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        keywords.addAll(Arrays.asList(rs.getString("keywords").split(",")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(keywords);
    }

    public static Event findEventbyID(Integer id) {
        try (Connection connection = H2DatabaseConnection.getConnection()) {
            String searchSQL = "SELECT * FROM events WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, id);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        EventRepositoryImpl eventrepo = new EventRepositoryImpl();
                        return new Event(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("owner_id"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getString("location"),
                            rs.getString("category"),
                            eventrepo.stringList(rs.getString("keywords")),
                            eventrepo.stringList(rs.getString("tags")),
                            eventrepo.stringList(rs.getString("images"))
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}