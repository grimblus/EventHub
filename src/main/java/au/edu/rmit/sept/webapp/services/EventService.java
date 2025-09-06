package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.repository.EventRepo;

import java.util.List;

@Service
public class EventService {
    private final EventRepo repo;

    @Autowired
    public EventService(EventRepo repo) {
        this.repo = repo;
    }

    public List<Event> findAll() {
        return this.repo.findAll();
    }

    public Event findById(int id) {
        return this.repo.findById(id).orElse(null);
    }

    public Event create(Event event) {
        return this.repo.save(event);
    }

    public void deleteById(int id) {
        this.repo.deleteById(id);
    }
}
