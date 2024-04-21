package cout.dev.projetcuisine.services;

import cout.dev.projetcuisine.security.GoogleTokenVerifier;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GoogleTokenVerifierService {

    private GoogleTokenVerifier verifier;

    public GoogleTokenVerifierService() {
        verifier = new GoogleTokenVerifier();
    }

    public String verifyToken(String token) throws GeneralSecurityException, IOException, InterruptedException {
        String idToken = verifier.verifyToken(token);
        if (idToken != null) {
            return idToken;
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }
}
