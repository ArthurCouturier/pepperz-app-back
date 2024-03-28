package cout.dev.projetcuisine.services;

import cout.dev.projetcuisine.dtos.PepperDTO;
import cout.dev.projetcuisine.models.Pepper;
import cout.dev.projetcuisine.repositories.PepperRepository;
import cout.dev.projetcuisine.utils.PepperSpecifications;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@Service
@RequestMapping("/api/peppers")
public class PepperService {

    private PepperRepository pepperRepository;


    public PepperService(PepperRepository pepperRepository) {
        this.pepperRepository = pepperRepository;
    }

    @PostMapping("/create")
    public Pepper create(@RequestBody PepperDTO pepperDTO) {
        System.out.println("Request to create a new ingredient.");
        Pepper pepper = Pepper.fromDTO(pepperDTO);
        return pepperRepository.save(pepper);
    }

    @GetMapping("/getAll")
    public List<Pepper> getAll() {
        return pepperRepository.findAll();
    }

    @GetMapping("/getByUuid/{uuid}")
    public Pepper getByUuid(@PathVariable String uuid) {
        return pepperRepository.findByUuid(UUID.fromString(uuid));
    }

    @GetMapping("/getBySpecification/{spec}")
    public List<Pepper> getBySpecification(@PathVariable String spec) {
        if (!PepperSpecifications.isValid(spec)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid specification: " + spec);
        }
        return pepperRepository.findBySpecificationsContains(spec);
    }

    @DeleteMapping("/deleteByUUid/{uuid}")
    public String deleteByUuid(@PathVariable String uuid) {
        Pepper pepper = pepperRepository.findByUuid(UUID.fromString(uuid));
        pepperRepository.delete(pepper);
        return "Pepper deleted successfully";
    }

    @DeleteMapping("/deleteByName/{uuid}")
    public String deleteByName(@PathVariable String name) {
        Pepper pepper = pepperRepository.findByName(name);
        pepperRepository.delete(pepper);
        return "Pepper deleted successfully";
    }

    @PutMapping("/update/{uuid}")
    public Pepper update(@RequestBody PepperDTO pepperDTO, @PathVariable String uuid) {
        Pepper pepper = pepperRepository.findByUuid(UUID.fromString(uuid));

        if (!pepperDTO.getSpecifications().isEmpty()) {
            for (String spec : pepperDTO.getSpecifications().split(";")) {
                if (!PepperSpecifications.isValid(spec)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid specification: " + spec);
                }
            }
        }

        Pepper updatedPepper = Pepper.fromDTO(pepperDTO);
        updatedPepper.setUuid(pepper.getUuid());
        return pepperRepository.save(updatedPepper);
    }
}
