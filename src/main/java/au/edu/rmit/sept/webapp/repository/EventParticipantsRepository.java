package au.edu.rmit.sept.webapp.repository;

public interface EventParticipantsRepository {
    public List<Integer> getEventParticipants(Integer eventId);
    public void addEventParticipant(Integer eventId, Integer userId);
}
