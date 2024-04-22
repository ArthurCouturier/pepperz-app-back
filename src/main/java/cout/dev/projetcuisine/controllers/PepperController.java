package cout.dev.projetcuisine.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cout.dev.projetcuisine.dtos.PepperDTO;
import cout.dev.projetcuisine.models.Pepper;
import cout.dev.projetcuisine.services.PepperService;

@RestController
@RequestMapping("/api/peppers")
public class PepperController {

    @Value("${app.admin.secret}")
    private String appAdminSecret;

    public PepperService pepperService;

    public PepperController(PepperService pepperService) {
        this.pepperService = pepperService;
    }

    @PostMapping("/create")
    public Pepper create(@RequestBody PepperDTO pepperDTO) {
        System.out.println("Request to create a new ingredient.");
        Pepper pepper = Pepper.fromDTO(pepperDTO);
        return pepperService.create(pepper);
    }

    @GetMapping("/getAll")
    public List<Pepper> getAll(@RequestHeader("Authorization") String authorizationHeader) {
        if (!authorizationHeader.equals(appAdminSecret)) {
            throw new RuntimeException("Unauthorized");
        }

        return pepperService.getAll();
    }

    @GetMapping("/getAllValidated")
    public List<Pepper> getAllValidated() {
        return pepperService.getAllValidated();
    }

    @GetMapping("/getByUuid/{uuid}")
    public Pepper getByUuid(@PathVariable String uuid) {
        return pepperService.getByUuid(UUID.fromString(uuid));
    }

    @GetMapping("/getBySpecification/{spec}")
    public List<Pepper> getBySpecification(@PathVariable String spec) {
        return pepperService.getBySpecification(spec);
    }

    @PutMapping("/update/{uuid}")
    public Pepper update(@RequestBody PepperDTO pepperDTO, @PathVariable String uuid) {
        Pepper pepper = pepperService.getByUuid(UUID.fromString(uuid));
        pepperService.checkSpecifications(pepperDTO.getSpecifications());
        return pepperService.update(pepper, pepperDTO);
    }

    @DeleteMapping("/deleteByUUid/{uuid}")
    public String deleteByUuid(@PathVariable String uuid) {
        return pepperService.deleteByUuid(UUID.fromString(uuid));
    }

    @DeleteMapping("/deleteByName/{uuid}")
    public String deleteByName(@PathVariable String name) {
        return pepperService.deleteByName(name);
    }

    @PutMapping("/validate-pepper/{uuid}")
    public Pepper validatePepper(
        @RequestHeader("Authorization") String authorizationHeader,
        @PathVariable String uuid
    ) {
        if (!authorizationHeader.equals(appAdminSecret)) {
            throw new RuntimeException("Unauthorized");
        }

        return pepperService.setValidationByUuid(UUID.fromString(uuid), true);
    }

    @PutMapping("/unvalidate-pepper/{uuid}")
    public Pepper unvalidatePepper(
        @RequestHeader("Authorization") String authorizationHeader,
        @PathVariable String uuid
    ) {
        if (!authorizationHeader.equals(appAdminSecret)) {
            throw new RuntimeException("Unauthorized");
        }

        return pepperService.setValidationByUuid(UUID.fromString(uuid), false);
    }
}
