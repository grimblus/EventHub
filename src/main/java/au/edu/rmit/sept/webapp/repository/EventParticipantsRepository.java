package au.edu.rmit.sept.webapp.repository;

public interface EventParticipantsRepository {
    
    public void addEventParticipant(Integer eventId, Integer userId);
}
