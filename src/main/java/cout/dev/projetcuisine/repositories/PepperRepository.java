package cout.dev.projetcuisine.repositories;

import cout.dev.projetcuisine.models.Pepper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PepperRepository extends JpaRepository<Pepper, UUID> {
    long deleteByUuid(UUID uuid);

    long deleteByName(String name);

    Pepper findByUuid(UUID uuid);

    Pepper findByName(String name);

    List<Pepper> findBySpecificationsContains(String specifications);
}
