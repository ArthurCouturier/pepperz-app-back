package cout.dev.projetcuisine.models.ingredients;

import cout.dev.projetcuisine.dtos.IngredientDTO;
import cout.dev.projetcuisine.utils.IngredientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "type")
    private IngredientType type;

    @Column(name = "desc")
    private String desc;


    public static Ingredient fromDTO(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDTO.getName());
        ingredient.setType(ingredientDTO.getType());
        ingredient.setDesc(ingredientDTO.getDesc());
        return ingredient;
    }

    public IngredientDTO toDTO() {
        return new IngredientDTO(
                this.name,
                this.type,
                this.desc
        );
    }
}
