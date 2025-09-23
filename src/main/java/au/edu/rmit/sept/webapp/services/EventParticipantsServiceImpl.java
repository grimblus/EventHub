package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.repository.EventParticipantsRepository;

import java.util.List;

@Service
public class EventParticipantsServiceImpl implements EventParticipantsService {
    private final EventParticipantsRepository repository;

     @Autowired
    public EventParticipantsServiceImpl(EventParticipantsRepository repository) {this.repository = repository;}
    public List<Integer> getEventParticipants(Integer eventId) {return repository.getEventParticipants(eventId);}
    @Override
    public void addEventParticipant(Integer eventId, Integer userId) {
      repository.addEventParticipant(eventId, userId);
    }
    @Override
    public List<Integer> getRSVPList(Integer userId) {
        return repository.getRSVPList(userId);
    }



}
