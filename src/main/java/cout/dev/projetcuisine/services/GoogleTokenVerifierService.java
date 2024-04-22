package cout.dev.projetcuisine.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cout.dev.projetcuisine.models.GoogleUser;

@Service
public class GoogleTokenVerifierService {

    public GoogleTokenVerifierService() {
    }

    public GoogleUser getGoogleUserVerifyingToken(String accessToken) throws GeneralSecurityException, IOException, InterruptedException {
        JsonNode googleUserNode = tryVerifyToken(accessToken);
        if (googleUserNode != null) {
            return getGoogleUser(googleUserNode, accessToken);
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }

    public JsonNode tryVerifyToken(String accessToken) throws GeneralSecurityException, IOException, InterruptedException {
        if (accessToken.contains("Bearer ")) {
            accessToken = accessToken.substring("Bearer ".length());
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/oauth2/v1/userinfo?alt=json"))
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode token = mapper.readTree(response.body());
            return token;
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }

    public GoogleUser getGoogleUser(JsonNode googleUserNode, String accessToken) {
        GoogleUser googleUser = new GoogleUser();
        googleUser.setId(googleUserNode.get("id").asText());
        googleUser.setEmail(googleUserNode.get("email").asText());
        googleUser.setVerifiedEmail(googleUserNode.get("verified_email").asBoolean());
        googleUser.setName(googleUserNode.get("name").asText());
        googleUser.setFamilyName(googleUserNode.get("family_name").asText());
        googleUser.setGivenName(googleUserNode.get("given_name").asText());
        googleUser.setPicture(googleUserNode.get("picture").asText());
        googleUser.setAccessToken(accessToken);
        googleUser.setShouldBeAdmin(false);
        return googleUser;
    }
}
