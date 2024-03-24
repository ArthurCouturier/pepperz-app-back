package cout.dev.projetcuisine.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import cout.dev.projetcuisine.utils.PepperSpecifications;
import cout.dev.projetcuisine.utils.PepperType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PepperDTO {

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "type", required = true)
    private PepperType type;

    @Column(name = "origin")
    private String origin;

    @JsonProperty(namespace = "desc")
    private String desc;

    @JsonProperty(namespace = "kgPrice")
    private int kgPrice;

    @JsonProperty(namespace = "specifications")
    private PepperSpecifications[] specifications;
}
