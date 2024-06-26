package cout.dev.projetcuisine.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cout.dev.projetcuisine.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    User findByEmail(String email);

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByGoogleToken(String googleToken);
}
