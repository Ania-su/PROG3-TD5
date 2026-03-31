package k2.ania.ingredient_again.service;

import k2.ania.ingredient_again.entity.Dish;
import k2.ania.ingredient_again.entity.DishIngredient;
import k2.ania.ingredient_again.entity.Ingredient;
import k2.ania.ingredient_again.exceptions.BadRequestException;
import k2.ania.ingredient_again.exceptions.NotFoundException;
import k2.ania.ingredient_again.repository.DishRepository;
import k2.ania.ingredient_again.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Dish findDishById(int dishId) throws SQLException {
        Dish dish = dishRepository.findDishById(dishId);
        if (dish == null) {
            throw new NotFoundException("Dish.id = {" + dishId + "} is not found");
        }
        return dish;
    }

    public Dish attachAndDetachIngredient(int dishId, List<Ingredient> ingredients) throws SQLException {

        if (ingredients == null || ingredients.isEmpty()) {
            throw new BadRequestException("Missing request body");
        }

        Dish dish = dishRepository.findDishById(dishId);
        if (dish == null) {
            throw new NotFoundException("Dish.id = {" + dishId + "} is not found");
        }

        for (Ingredient ingredient : ingredients) {

            Ingredient existing = ingredientRepository.findIngredientById(ingredient.getId());
            if (existing == null) continue;

            boolean alreadyLinked = dish.getIngredients().stream()
                    .anyMatch(di -> di.getIngredient().getId() == existing.getId());

            if (alreadyLinked) {
                dishRepository.detachIngredient(dishId, existing.getId());
            } else {
                dishRepository.attachIngredient(dishId, existing.getId());
            }
        }

        return dishRepository.findDishById(dishId);
    }
}
