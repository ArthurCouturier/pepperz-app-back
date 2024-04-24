package cout.dev.projetcuisine.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cout.dev.projetcuisine.dtos.PepperRateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pepper_rates")
public class PepperRate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "rate")
    private int rate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "pepper")
    @JsonIgnore
    private Pepper pepper;

    @ManyToOne
    @JoinColumn(name = "\"user\"")
    @JsonIgnore
    private User user;

    public PepperRateDTO toDTO() {
        PepperRateDTO pepperRateDTO = new PepperRateDTO();
        pepperRateDTO.setUuid(getUuid());
        pepperRateDTO.setRate(getRate());
        pepperRateDTO.setComment(getComment());
        pepperRateDTO.setPepperUuid(getPepper().getUuid().toString());
        pepperRateDTO.setUserEmail(getUser().getEmail());
        return pepperRateDTO;
    }
}
