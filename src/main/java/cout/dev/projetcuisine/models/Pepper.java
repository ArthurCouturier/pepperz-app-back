package cout.dev.projetcuisine.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cout.dev.projetcuisine.dtos.PepperDTO;
import cout.dev.projetcuisine.utils.PepperTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "peppers")
public class Pepper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "type")
    private PepperTypes type;

    @Column(name = "origin")
    private String origin;

    @Column(name = "desc")
    private String desc;

    @Column(name = "kgPrice")
    private int kgPrice;

    @Column(name = "specifications")
    private String specifications;

    @Column(name = "validated_by_admin")
    private boolean validatedByAdmin = false;

    @OneToMany(mappedBy = "pepper")
    private List<PepperRate> pepperRates = new ArrayList<PepperRate>();

    @Column(name = "global_rate")
    private float globalRate;


    public static Pepper fromDTO(PepperDTO pepperDTO) {
        Pepper pepper = new Pepper();
        String name = pepperDTO.getName().substring(0, 1).toUpperCase() + pepperDTO.getName().substring(1);
        pepper.setName(name);
        pepper.setType(pepperDTO.getType());
        String origin = pepperDTO.getOrigin().substring(0, 1).toUpperCase() + pepperDTO.getOrigin().substring(1);
        pepper.setOrigin(origin);
        String desc = pepperDTO.getDesc().substring(0, 1).toUpperCase() + pepperDTO.getDesc().substring(1);
        pepper.setDesc(desc);
        pepper.setKgPrice(pepperDTO.getKgPrice());
        pepper.setSpecifications(pepperDTO.getSpecifications());
        pepper.setValidatedByAdmin(false);
        pepper.setPepperRates(new ArrayList<PepperRate>());
        return pepper;
    }

    public PepperDTO toDTO() {
        return new PepperDTO(
                this.name,
                this.type,
                this.origin,
                this.desc,
                this.kgPrice,
                this.specifications
        );
    }
}
