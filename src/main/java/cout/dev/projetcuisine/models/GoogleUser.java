package cout.dev.projetcuisine.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @Column(name = "id")
    private String id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "verified_email")
    private Boolean verified_email;

    @Column(name = "name")
    private String name;

    @Column(name = "family_name")
    private String family_name;

    @Column(name = "given_name")
    private String given_name;

    @Column(name = "picture")
    private String picture;
}
