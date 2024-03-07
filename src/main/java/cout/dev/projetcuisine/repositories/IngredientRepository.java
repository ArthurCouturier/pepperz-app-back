package cout.dev.projetcuisine.repositories;

import cout.dev.projetcuisine.models.ingredients.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    long deleteByUuid(UUID uuid);
}
