package cout.dev.projetcuisine.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "google_users")
public class GoogleUser {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "verified_email")
    private Boolean verifiedEmail;

    @Column(name = "name")
    private String name;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "picture")
    private String picture;

    @Column(name = "access_token")
    private String accessToken;
}
