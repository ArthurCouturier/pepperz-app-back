package cout.dev.projetcuisine.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cout.dev.projetcuisine.dtos.PepperDTO;
import cout.dev.projetcuisine.models.Pepper;
import cout.dev.projetcuisine.repositories.PepperRepository;
import cout.dev.projetcuisine.utils.PepperSpecifications;

@Service
public class PepperService {

    private PepperRepository pepperRepository;

    public PepperService(PepperRepository pepperRepository) {
        this.pepperRepository = pepperRepository;
    }

    public Pepper create(Pepper pepper) {
        return pepperRepository.save(pepper);
    }

    public List<Pepper> getAll() {
        return pepperRepository.findAll();
    }

    public Pepper getByUuid(UUID uuid) {
        return pepperRepository.findByUuid(uuid);
    }

    public List<Pepper> getBySpecification(String spec) {
        if (!PepperSpecifications.isValid(spec)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid specification: " + spec);
        }
        return pepperRepository.findBySpecificationsContains(spec);
    }

    public void checkSpecifications(String specifications) {
        if (!specifications.isEmpty()) {
            for (String spec : specifications.split(";")) {
                if (!PepperSpecifications.isValid(spec)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid specification: " + spec);
                }
            }
        }
    }

    public Pepper update(Pepper pepper, PepperDTO pepperDTO) {
        Pepper updatedPepper = Pepper.fromDTO(pepperDTO);
        updatedPepper.setUuid(pepper.getUuid());
        return pepperRepository.save(updatedPepper);
    }

    public String deleteByUuid(UUID uuid) {
        Pepper pepper = pepperRepository.findByUuid(uuid);
        pepperRepository.delete(pepper);
        return "Pepper deleted successfully";
    }

    public String deleteByName(String name) {
        Pepper pepper = pepperRepository.findByName(name);
        pepperRepository.delete(pepper);
        return "Pepper deleted successfully";
    }
}
