package au.edu.rmit.sept.webapp.services;

import java.util.HashSet;

public interface UserInterestsService {
    public void addUserInterests( int user_id, String interest);
    public HashSet<String> getUserInterests( int user_id);
}
