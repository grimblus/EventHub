package au.edu.rmit.sept.webapp.repository;
import au.edu.rmit.sept.webapp.entity.Event;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private final DataSource dataSource;

    public List<String> stringList(String list) {
        List<String> finalList = new ArrayList<>();
        for (String s : list.split(","))
            finalList.add(s);
        return finalList;
    }

    public EventRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addEvent(Event event) {
        try (Connection connection = this.dataSource.getConnection()) {
            String insertSQL = "INSERT INTO events (title, description, ownerID, date, location, ticketPrice, category, keywords, tags, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, event.getTitle());
                ps.setString(2, event.getDescription());
                ps.setInt(3, event.getOwnerID());
                ps.setTimestamp(4, Timestamp.valueOf(event.getDate()));
                ps.setString(5, event.getLocation());
                ps.setString(6, event.getTicketPrice());
                ps.setString(7, event.getCategory());
                ps.setString(8, String.join(",", event.getKeywords()));
                ps.setString(9, String.join(",", event.getTags()));
                ps.setString(10, String.join(",", event.getImages()));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
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
                                rs.getString("ticketPrice"),
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

        try( Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(searchSQL.toString())) {
                ps.setInt(1, id);
                ps.setString(2, name.toLowerCase());

                try (ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        event = new Event(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("ownerID"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getString("location"),
                            rs.getString("ticketPrice"),
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
            parameters.add("%" + name.toLowerCase() + "%");
        }
        if (tags != null && !tags.isEmpty()) {
            StringBuilder addSQL = new StringBuilder("AND (");
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0) { addSQL.append(" OR "); }
                addSQL.append("LOWER(tags) LIKE ?");
                parameters.add("%" + tags.get(i).toLowerCase() + "%");
            }
            addSQL.append(")");
            searchSQL.append(addSQL);
        }
        if (location != null && !location.isEmpty()) {
            searchSQL.append(" AND LOWER(location) LIKE ?");
            parameters.add("%" + location.toLowerCase() + "%");
        }
        if (category != null && !category.isEmpty()) {
            searchSQL.append(" AND LOWER(category) LIKE ?");
            parameters.add("%" + category.toLowerCase() + "%");
        }
        if (keywords != null && !keywords.isEmpty()) {
            StringBuilder addSQL = new StringBuilder("AND (");
            for (int i = 0; i < keywords.size(); i++) {
                if (i > 0) { addSQL.append(" OR "); }
                addSQL.append("LOWER(keywords) LIKE ?");
                parameters.add("%" + keywords.get(i).toLowerCase() + "%");
            }
            addSQL.append(")");
            searchSQL.append(addSQL);
        }

        List<Event> events = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
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
                                rs.getString("ticketPrice"),
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
        try (Connection connection = this.dataSource.getConnection()) {
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
        Set<String> keywords = new HashSet<String>();
        try (Connection connection = this.dataSource.getConnection()) {
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

    public Event findEventbyID(Integer id) {
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM events WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, id);
                try  (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Event(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("ownerID"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getString("location"),
                            rs.getString("ticketPrice"),
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
        return null;
    }

    public boolean deleteEventbyID(Integer id) {
        try (Connection connection = this.dataSource.getConnection()) {
            String deleteSQL = "DELETE FROM events WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // update event image list (delimited ,)
    public boolean updateEventImages(int id, List<String> images) {
        try (Connection connection = this.dataSource.getConnection()) {
            String sql = "UPDATE events SET images = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, String.join(",", images));
                ps.setInt(2, id);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // delete image from list
    public boolean deleteImageForEvent(int id, int index) {
        // fetch current images
        Event ev = findEventbyID(id);
        if (ev == null) return false;
        List<String> imgs = new ArrayList<>(ev.getImages());
        if (index < 0 || index >= imgs.size()) return false;

        // delete by index
        imgs.remove(index); 
        return updateEventImages(id, imgs);
    }

    public Integer getOwnerID(Integer eventId) {
        try (Connection connection = this.dataSource.getConnection()) {
            String sql = "SELECT ownerID FROM events WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, eventId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("ownerID");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addImageForEvent(int eventId, String filename) {
        Event ev = findEventbyID(eventId);
        if (ev == null) return false;
        List<String> imgs = new java.util.ArrayList<>(ev.getImages());
        imgs.add(filename);
        return updateEventImages(eventId, imgs);
    }

    public List<Event> getEventsByOwnerId(int id) {
        List<Event> events = new ArrayList<>();
        try (Connection connection = this.dataSource.getConnection()) {
            String searchSQL = "SELECT * FROM events WHERE ownerId = ?";

            try (PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, id);
                try  (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Event event = new Event(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getInt("ownerID"),
                                rs.getTimestamp("date").toLocalDateTime(),
                                rs.getString("location"),
                                rs.getString("ticketPrice"),
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