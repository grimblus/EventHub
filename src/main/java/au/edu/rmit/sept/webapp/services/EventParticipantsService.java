package au.edu.rmit.sept.webapp.services;

import java.util.List;

public interface EventParticipantsService {
    public List<Integer> getEventParticipants(Integer eventId);
    public void addEventParticipant(Integer eventId, Integer userId); 
}
