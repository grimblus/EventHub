package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Event;

import java.util.List;


public interface EventRepository {
    public List<String> stringList(String list);
    public void setEvents();
    public void addEvent(Event event);
    public List<Event> getEvents();
    public Event getEvent(int id, String name);
    public List<Event> getEventsByFilter(String name, List<String> tags, String location, String category, List<String> keywords);
    public List<String> getTags();
    public List<String> getKeywords();
    public Event findEventbyID(Integer id);
    public boolean updateEventImages(int id, List<String> images);
    public boolean deleteEventbyID(Integer id);
    public Integer getOwnerID(Integer eventId);
}
