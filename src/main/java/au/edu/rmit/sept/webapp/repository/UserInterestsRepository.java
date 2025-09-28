package au.edu.rmit.sept.webapp.repository;

import java.util.HashSet;

public interface UserInterestsRepository {
    
    public void setUserInterests();
    public HashSet<String> getUserInterests(int user_id);
    public void addUserInterests(int user_id, String interest);
}
