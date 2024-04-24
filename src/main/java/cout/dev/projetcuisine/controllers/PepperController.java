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
import cout.dev.projetcuisine.dtos.PepperRateDTO;
import cout.dev.projetcuisine.models.Pepper;
import cout.dev.projetcuisine.models.PepperRate;
import cout.dev.projetcuisine.models.User;
import cout.dev.projetcuisine.services.PepperService;
import cout.dev.projetcuisine.services.UserService;


@RestController
@RequestMapping("/api/peppers")
public class PepperController {

    @Value("${app.admin.secret}")
    private String appAdminSecret;

    public final PepperService pepperService;

    public final UserService userService;

    public PepperController(
            PepperService pepperService,
            UserService userService) {
        this.pepperService = pepperService;
        this.userService = userService;
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

    @GetMapping("/getAllUnvalidated")
    public List<Pepper> getAllUnvalidated(@RequestHeader("Authorization") String accessToken) {
        if (userService.isUserAdminByGoogleAccessToken(accessToken)) {
            return pepperService.getAllUnvalidated();
        }
        throw new RuntimeException("Unauthorized");
    }
    

    @GetMapping("/getByUuid/{uuid}")
    public Pepper getByUuid(@PathVariable String uuid) {
        return pepperService.getByUuid(UUID.fromString(uuid));
    }

    @GetMapping("/getBySpecification/{spec}")
    public List<Pepper> getBySpecification(@PathVariable String spec) {
        return pepperService.getBySpecification(spec);
    }

    @GetMapping("/getAllRates")
    public List<PepperRateDTO> getMethodName() {
        return pepperService.getAllRates()
            .stream().map(PepperRate::toDTO).toList();
    }

    @PutMapping("/update/{uuid}")
    public Pepper update(@RequestBody PepperDTO pepperDTO, @PathVariable String uuid) {
        Pepper pepper = pepperService.getByUuid(UUID.fromString(uuid));
        pepperService.checkSpecifications(pepperDTO.getSpecifications());
        return pepperService.update(pepper, pepperDTO);
    }

    @DeleteMapping("/deleteByUUid/{uuid}")
    public String deleteByUuid(
        @RequestHeader("Authorization") String accessToken,
        @PathVariable String uuid
    ) {
        if (userService.isUserAdminByGoogleAccessToken(accessToken)) {
            return pepperService.deleteByUuid(UUID.fromString(uuid));
        }
        throw new RuntimeException("Unauthorized");
    }

    @DeleteMapping("/deleteByName/{name}")
    public String deleteByName(
        @RequestHeader("Authorization") String accessToken,
        @PathVariable String name
    ) {
        if (userService.isUserAdminByGoogleAccessToken(accessToken)) {
            return pepperService.deleteByName(name);
        }
        throw new RuntimeException("Unauthorized");
    }

    @GetMapping("/validate-pepper/{uuid}")
    public Pepper validatePepper(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable String uuid
    ) {
        System.out.println("Want to validate pepper with uuid: " + uuid);
        if (userService.isUserAdminByGoogleAccessToken(accessToken)) {
            return pepperService.setValidationByUuid(UUID.fromString(uuid), true);
        }
        throw new RuntimeException("Unauthorized");
    }

    @GetMapping("/unvalidate-pepper/{uuid}")
    public Pepper unvalidatePepper(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable String uuid) {
        if (userService.isUserAdminByGoogleAccessToken(accessToken)) {
            return pepperService.setValidationByUuid(UUID.fromString(uuid), false);
        }
        throw new RuntimeException("Unauthorized");
    }

    @PostMapping("/rate/{uuid}")
    public Pepper postMethodName(
        @PathVariable String uuid,
        @RequestHeader("email") String email, 
        @RequestBody String rate
    ) {
        
        // if (!userService.isUserAdminByGoogleAccessToken(accessToken)) {
        //     throw new RuntimeException("Unauthorized");
        // }
        System.out.println("Rate pepper with email: " + email);

        User user = userService.getUserByEmail(email);

        Pepper pepper = pepperService.getByUuid(UUID.fromString(uuid));
        
        return pepperService.rate(pepper, user, Integer.parseInt(rate));
    }
    
}
