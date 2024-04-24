package cout.dev.projetcuisine.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cout.dev.projetcuisine.dtos.PepperDTO;
import cout.dev.projetcuisine.models.Pepper;
import cout.dev.projetcuisine.models.PepperRate;
import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.repositories.PepperRateRepository;
import cout.dev.projetcuisine.repositories.PepperRepository;
import cout.dev.projetcuisine.utils.PepperSpecifications;

@Service
public class PepperService {

    private final PepperRepository pepperRepository;

    private final PepperRateRepository pepperRateRepository;

    private final UserService userService;

    public PepperService(
        PepperRepository pepperRepository,
        PepperRateRepository pepperRateRepository,
        UserService userService
    ) {
        this.pepperRepository = pepperRepository;
        this.pepperRateRepository = pepperRateRepository;
        this.userService = userService;
    }

    public Pepper create(Pepper pepper) {
        return pepperRepository.save(pepper);
    }

    public List<Pepper> getAll() {
        return pepperRepository.findAll();
    }

    public List<Pepper> getAllValidated() {
        return pepperRepository.findAll()
                .stream()
                .filter(Pepper::isValidatedByAdmin)
                .toList();
    }

    public List<Pepper> getAllUnvalidated() {
        return pepperRepository.findAll()
                .stream()
                .filter(pepper -> !pepper.isValidatedByAdmin())
                .toList();
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

    public List<PepperRate> getAllRates() {
        return pepperRateRepository.findAll();
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
        updatedPepper.setValidatedByAdmin(pepper.isValidatedByAdmin());
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

    public Pepper setValidationByUuid(UUID uuid, boolean validatedByAdmin) {
        Pepper pepper = getByUuid(uuid);
        if (pepper == null) {
            throw new RuntimeException("Pepper not found.");
        }

        pepper.setValidatedByAdmin(validatedByAdmin);
        System.out.println("Validate pepper:" + pepper);
        return pepperRepository.save(pepper);
    }

    public Pepper rate(Pepper pepper, User user, int rate) {
        PepperRate pepperRate = new PepperRate();
        pepperRate.setRate(rate);
        pepperRate.setPepper(pepper);
        pepperRate.setUser(user);

        userService.addRate(user, pepperRate);
        pepper.getPepperRates().add(pepperRate);
        
        pepperRateRepository.save(pepperRate);

        return pepperRepository.save(pepper);
    }
}
