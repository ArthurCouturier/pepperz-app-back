package cout.dev.projetcuisine.models;

import java.util.List;
import java.util.UUID;


import cout.dev.projetcuisine.utils.UserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "emailVerified")
    private Boolean emailVerified;

    @Column(name = "name")
    private String name;

    @Column(name = "token")
    private String googleToken;

    @Column(name = "appToken")
    private String appToken;

    @Column(name = "registerdOn")
    private String registerdOn;

    @Column(name = "role")
    private UserRoles role;

    @OneToMany(mappedBy = "user")
    private List<PepperRate> pepperRates;
    
}
