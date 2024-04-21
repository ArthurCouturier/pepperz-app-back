package cout.dev.projetcuisine.services;

import org.springframework.stereotype.Service;

import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getGoogleUser(String email) {
        return userRepository.findByEmail(email);
    }
}
