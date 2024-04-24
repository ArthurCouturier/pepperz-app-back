package cout.dev.projetcuisine.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cout.dev.projetcuisine.models.PepperRate;

@Repository
public interface PepperRateRepository extends JpaRepository<PepperRate, UUID> {
    
}
