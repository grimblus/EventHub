package au.edu.rmit.sept.webapp.repository;

import java.util.ArrayList;
import java.util.HashSet;

public interface UserInterestsRepository {
    public HashSet<String> getUserInterests(int user_id);
    public void addUserInterests(int user_id, ArrayList<String> interest);
}
