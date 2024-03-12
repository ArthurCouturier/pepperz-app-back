package cout.dev.projetcuisine.repositories;

import cout.dev.projetcuisine.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByName(String name);
}
