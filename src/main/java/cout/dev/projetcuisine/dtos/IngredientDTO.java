package cout.dev.projetcuisine.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import cout.dev.projetcuisine.utils.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IngredientDTO {

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "type", required = true)
    private IngredientType type;

    @JsonProperty(namespace = "desc")
    private String desc;
}
