package cout.dev.projetcuisine.services;

import cout.dev.projetcuisine.dtos.IngredientDTO;
import cout.dev.projetcuisine.models.Ingredient;
import cout.dev.projetcuisine.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Service
@RequestMapping("/api/ingredients")
public class IngredientService {

    private IngredientRepository ingredientRepository;


    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping("/create")
    public Ingredient createVegetable(@RequestBody IngredientDTO ingredientDTO) {
        System.out.println("Request to create a new ingredient.");
        Ingredient ingredient = Ingredient.fromDTO(ingredientDTO);
        return ingredientRepository.save(ingredient);
    }

    @GetMapping("/getAll")
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @DeleteMapping("/deleteByUUid/{uuid}")
    public String deleteByUuid(@PathVariable String uuid) {
        Ingredient ingredient = ingredientRepository.findByUuid(UUID.fromString(uuid));
        ingredientRepository.delete(ingredient);
        return "Ingredient deleted successfully";
    }

    @DeleteMapping("/deleteByName/{uuid}")
    public String deleteByName(@PathVariable String name) {
        Ingredient ingredient = ingredientRepository.findByName(name);
        ingredientRepository.delete(ingredient);
        return "Ingredient deleted successfully";
    }
}
