package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.repository.UserInterestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class UserInterestsServiceImpl implements UserInterestsService {

    private final UserInterestsRepository userInterestsRepository;

    @Autowired
    public UserInterestsServiceImpl(UserInterestsRepository userInterestsRepository) {
        this.userInterestsRepository = userInterestsRepository;
    }

    public void addUserInterests( int user_id, ArrayList<String> interest) {
        userInterestsRepository.addUserInterests(user_id, interest);
    }
    public HashSet<String> getUserInterests(int user_id) {
        return userInterestsRepository.getUserInterests(user_id);

    }
}
