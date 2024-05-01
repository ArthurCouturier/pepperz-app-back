package cout.dev.projetcuisine.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import cout.dev.projetcuisine.utils.PepperTypes;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PepperDTO {

    @JsonProperty(namespace = "name", required = true)
    private String name;

    @JsonProperty(namespace = "type", required = true)
    private PepperTypes type;

    @Column(name = "origin")
    private String origin;

    @JsonProperty(namespace = "desc")
    private String desc;

    @JsonProperty(namespace = "kgPrice")
    private int kgPrice;

    @JsonProperty(namespace = "specifications")
    private String specifications;
}
