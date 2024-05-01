package cout.dev.projetcuisine.dtos;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PepperRateDTO {
    private UUID uuid;
    private int rate;
    private String comment;
    private String pepperUuid;
    private String userEmail;
}
