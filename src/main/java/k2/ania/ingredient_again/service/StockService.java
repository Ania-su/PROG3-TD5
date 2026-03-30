package k2.ania.ingredient_again.service;

import k2.ania.ingredient_again.entity.DishIngredient;
import k2.ania.ingredient_again.entity.Ingredient;
import k2.ania.ingredient_again.entity.StockValue;
import k2.ania.ingredient_again.exceptions.BadRequestException;
import k2.ania.ingredient_again.exceptions.NotFoundException;
import k2.ania.ingredient_again.repository.IngredientRepository;
import k2.ania.ingredient_again.repository.StockMovementRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor

@Service
public class StockService {
    private final StockMovementRepository stockMovementRepository;
    private final IngredientRepository ingredientRepository;

    public StockValue findStockIngredient (int id, Instant t, DishIngredient.Unit unit) throws BadRequestException {
        Ingredient ingredient = ingredientRepository.findIngredientById(id);

        if(t == null || unit==null) {
            throw new BadRequestException("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        if(ingredient == null) {
            throw new NotFoundException("Ingredient.id = {" + id + "} is not found");
        }

        return stockMovementRepository.findIngredientById(id, t, unit);
    }
}
