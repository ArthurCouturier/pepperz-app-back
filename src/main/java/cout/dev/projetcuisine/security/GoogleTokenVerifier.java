package cout.dev.projetcuisine.security;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.GeneralSecurityException;

public class GoogleTokenVerifier {

    public GoogleTokenVerifier() {
    }

    public String verifyToken(String accessToken) throws GeneralSecurityException, IOException, InterruptedException {
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
            String token = response.body();
            return token;
        } else {
            return response.body();
        }
    }
}
