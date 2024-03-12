package cout.dev.projetcuisine.repositories;

import cout.dev.projetcuisine.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    long deleteByUuid(UUID uuid);

    long deleteByName(String name);

    Ingredient findByUuid(UUID uuid);

    Ingredient findByName(String name);
}
