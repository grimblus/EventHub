package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.repository.EventRepository;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Autowired
    public EventServiceImpl(EventRepository repository) {this.repository = repository;}

    @Override
    public List<String> stringList(String list) {return repository.stringList(list);}
    @Override
    public void setEvents() {repository.setEvents();}
    public void addEvent(Event event) {repository.addEvent(event);}
    public List<Event> getEvents() {return repository.getEvents();}
    public Event getEvent(int id, String name) { return repository.getEvent(id, name); }
    public List<Event> getEventsByFilter(String name, List<String> tags, String location, String category, List<String> keywords) {return repository.getEventsByFilter(name, tags, location, category, keywords);}
    public List<String> getTags() {return repository.getTags();}
    public List<String> getKeywords() {return repository.getKeywords();}
    public Event findEventbyID(Integer id) {return repository.findEventbyID(id);}
    public boolean deleteEventbyID(Integer id) {return repository.deleteEventbyID(id);}
}
