package cout.dev.projetcuisine.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getUser(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String token = authorizationHeader.substring("Bearer ".length());
        try {
            return tokenVerifierService.verifyToken(token);
        } catch (Exception e) {
            return "Access denied" + e.getMessage();
        }
    }
    
}
