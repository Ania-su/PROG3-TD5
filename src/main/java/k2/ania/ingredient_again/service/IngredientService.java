package k2.ania.ingredient_again.service;

import k2.ania.ingredient_again.entity.Ingredient;
import k2.ania.ingredient_again.exceptions.NotFoundException;
import k2.ania.ingredient_again.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient findIngredientById(int id) throws NotFoundException {
        Ingredient ingredient = ingredientRepository.findIngredientById(id);
        if(ingredient == null) {
            throw new NotFoundException("Ingredient.id = {" + id + "} is not found");
        }
        return ingredient;
    }
}
