package au.edu.rmit.sept.webapp.repository;

import java.util.List;

public interface EventParticipantsRepository {
    public List<Integer> getEventParticipants(Integer eventId);
    public void addEventParticipant(Integer eventId, Integer userId);
    public List<Integer> getRSVPList(Integer userId);
}
