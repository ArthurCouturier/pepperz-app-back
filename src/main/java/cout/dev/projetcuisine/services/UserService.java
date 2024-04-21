package cout.dev.projetcuisine.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cout.dev.projetcuisine.models.GoogleUser;
import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.repositories.GoogleUserRepository;
import cout.dev.projetcuisine.repositories.UserRepository;
import cout.dev.projetcuisine.utils.UserRoles;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final GoogleUserRepository googleUserRepository;
    
    public UserService(
        UserRepository userRepository,
        GoogleUserRepository googleUserRepository
    ) {
        this.userRepository = userRepository;
        this.googleUserRepository = googleUserRepository;
    }

    public User createUser(GoogleUser user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setEmailVerified(user.getVerifiedEmail());
        newUser.setName(user.getName());
        newUser.setGoogleToken(user.getAccessToken());
        newUser.setRegisterdOn("GOOGLE");
        newUser.setRole(UserRoles.MEMBER);
        googleUserRepository.save(user);
        return userRepository.save(newUser);
    }

    public User getGoogleUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public GoogleUser updateGoogleToken(User user, String googleAccessToken) {
        user.setGoogleToken(googleAccessToken);
        GoogleUser googleUser = googleUserRepository.findByEmail(user.getEmail()).get();
        googleUser.setAccessToken(googleAccessToken);
        googleUserRepository.save(googleUser);
        userRepository.save(user);
        return googleUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<GoogleUser> getAllGoogleUsers() {
        return googleUserRepository.findAll();
    }
}
