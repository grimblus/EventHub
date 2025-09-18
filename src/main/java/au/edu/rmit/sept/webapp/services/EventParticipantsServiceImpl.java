package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.repository.EventParticipantsRepository;
import au.edu.rmit.sept.webapp.repository.EventRepository;

@Service
public class EventParticipantsServiceImpl implements EventParticipantsService {
    private final EventParticipantsRepository repository;

     @Autowired
    public EventParticipantsServiceImpl(EventParticipantsRepository repository) {this.repository = repository;}

    @Override
    public void addEventParticipant(Integer eventId, Integer userId) {
      repository.addEventParticipant(eventId, userId);
    }
    
}
