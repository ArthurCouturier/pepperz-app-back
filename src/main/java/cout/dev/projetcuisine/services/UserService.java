package cout.dev.projetcuisine.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;

import cout.dev.projetcuisine.models.GoogleUser;
import cout.dev.projetcuisine.models.PepperRate;
import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.repositories.GoogleUserRepository;
import cout.dev.projetcuisine.repositories.UserRepository;
import cout.dev.projetcuisine.utils.UserRoles;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final GoogleUserRepository googleUserRepository;

    private final GoogleTokenVerifierService googleTokenVerifierService;
    
    public UserService(
        UserRepository userRepository,
        GoogleUserRepository googleUserRepository,
        GoogleTokenVerifierService googleTokenVerifierService
    ) {
        this.userRepository = userRepository;
        this.googleUserRepository = googleUserRepository;
        this.googleTokenVerifierService = googleTokenVerifierService;
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

    private User getByGoogleAccessToken(String accessToken) {
        return userRepository.findByGoogleToken(accessToken).get();
    }

    public User getByToken(String accessToken) {
        return getByGoogleAccessToken(accessToken);
    }

    public User passUserAdminByEmail(String email) {
        User user = userRepository.findByEmail(email);
        user.setRole(UserRoles.ADMIN);
        GoogleUser googleUser = googleUserRepository.findByEmail(email).get();
        googleUser.setShouldBeAdmin(true);
        googleUserRepository.save(googleUser);
        return userRepository.save(user);
    }

    public Boolean isUserAdminByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user.getRole().equals(UserRoles.ADMIN);
    }

    public Boolean isUserAdminByGoogleAccessToken(String accessToken) {
        JsonNode googleUserNode;
        try {
            googleUserNode = googleTokenVerifierService.tryVerifyToken(accessToken);
        } catch (GeneralSecurityException | IOException | InterruptedException e) {
            throw new RuntimeException("Unauthorized");
        }

        String email = googleUserNode.get("email").asText();

        if (!(getUserByEmail(email).getRole() == UserRoles.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return true;
    }

    public User addRate(User user, PepperRate rate) {
        user.getPepperRates().add(rate);
        return userRepository.save(user);
    }

    public List<PepperRate> getRates(User user) {
        return user.getPepperRates();
    }
}
