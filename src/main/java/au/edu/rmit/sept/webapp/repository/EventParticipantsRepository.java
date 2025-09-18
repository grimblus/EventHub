package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Event;

import java.util.List;

public interface EventParticipantsRepository {
    
    public void addEventParticipant(Integer eventId, Integer userId);
}
