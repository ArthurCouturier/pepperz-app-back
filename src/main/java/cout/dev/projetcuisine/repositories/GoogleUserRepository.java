package cout.dev.projetcuisine.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cout.dev.projetcuisine.models.GoogleUser;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUser, String> {
    
    Optional<GoogleUser> findByEmail(String email);

    Optional<GoogleUser> findById(String id);
}
