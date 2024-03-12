package cout.dev.projetcuisine.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "email", required = true)
    private String email;

    @JsonProperty(namespace = "password", required = true)
    private String password;
}
