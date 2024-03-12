package cout.dev.projetcuisine.models;

import cout.dev.projetcuisine.dtos.IngredientDTO;
import cout.dev.projetcuisine.utils.IngredientType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "ingredients")
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
        String name = ingredientDTO.getName().substring(0, 1).toUpperCase() + ingredientDTO.getName().substring(1);
        ingredient.setName(name);
        ingredient.setType(ingredientDTO.getType());
        String desc = ingredientDTO.getDesc().substring(0, 1).toUpperCase() + ingredientDTO.getDesc().substring(1);
        ingredient.setDesc(desc);
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
