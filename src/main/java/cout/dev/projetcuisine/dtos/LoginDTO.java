package cout.dev.projetcuisine.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDTO {

    @JsonProperty(namespace = "login", required = true)
    private String login;

    @JsonProperty(namespace = "password", required = true)
    private String password;


    public static boolean isLoginAnEmail(String login) {
        return login.contains("@");
    }
}
