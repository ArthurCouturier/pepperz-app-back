package cout.dev.projetcuisine.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cout.dev.projetcuisine.models.GoogleUser;
import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.services.GoogleTokenVerifierService;
import cout.dev.projetcuisine.services.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    private GoogleTokenVerifierService tokenVerifierService;

    public UserController(UserService userService, GoogleTokenVerifierService tokenVerifierService) {
        this.userService = userService;
        this.tokenVerifierService = tokenVerifierService;
    }

    @GetMapping("/auth")
    public String test() {
        return "ok";
    }

    @GetMapping("/auth/google")
    public GoogleUser getGoogleUser(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String googleAccessToken = authorizationHeader.substring("Bearer ".length());
        try {
            GoogleUser googleUser = tokenVerifierService.getGoogleUserVerifyingToken(googleAccessToken);
            User user = userService.getUserByEmail(googleUser.getEmail());
            if (user == null) {
                user = userService.createUser(googleUser);
            } else {
                googleUser = userService.updateGoogleToken(user, googleAccessToken);
            }
            return googleUser;
        } catch (Exception e) {
            throw new Exception("Invalid token.");
        }
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/google/getAll")
    public List<GoogleUser> getAllGoogleUsers() {
        return userService.getAllGoogleUsers();
    }
    
    
}
