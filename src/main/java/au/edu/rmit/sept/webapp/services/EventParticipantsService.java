package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.User;

import java.util.List;

public interface EventParticipantsService {
    public List<User> getEventParticipants(Integer eventId);
    public List<Integer> getRSVPList(Integer userId);

    public void addEventParticipant(Integer eventId, Integer userId); 

    public boolean PrintAttendeeCSV(Integer eventID);
}
