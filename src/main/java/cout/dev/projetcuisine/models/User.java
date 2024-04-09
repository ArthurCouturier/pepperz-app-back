package cout.dev.projetcuisine.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "hashedPassword")
    private String hashedPassword;

    @Column(name = "ratings")
    @OneToMany
    private List<UserPepperRating> ratings;

    @ManyToMany
    @JoinTable(
            name = "user_friendships",
            joinColumns = @JoinColumn(name = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "uuid")
    )
    private List<User> friends;
}
