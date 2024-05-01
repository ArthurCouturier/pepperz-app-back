package cout.dev.projetcuisine.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cout.dev.projetcuisine.models.Pepper;
import cout.dev.projetcuisine.models.PepperRate;
import cout.dev.projetcuisine.models.User;

@Repository
public interface PepperRateRepository extends JpaRepository<PepperRate, UUID> {

    PepperRate findByPepperAndUser(Pepper pepper, User user);

    List<PepperRate> findByUser(User user);
    
}
