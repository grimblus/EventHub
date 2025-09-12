package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Event;

import java.util.List;

public interface EventService {
    public List<String> stringList(String list);
    public void setEvents();
    public void addEvent(Event event);
    public List<Event> getEvents();
    public List<Event> getEventsByFilter(String name, List<String> tags, String location, String category, List<String> keywords);
    public List<String> getTags();
    public List<String> getKeywords();
}
