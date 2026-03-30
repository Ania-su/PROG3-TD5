package k2.ania.ingredient_again.controller;


import k2.ania.ingredient_again.entity.DishIngredient;
import k2.ania.ingredient_again.entity.Ingredient;
import k2.ania.ingredient_again.exceptions.BadRequestException;
import k2.ania.ingredient_again.exceptions.NotFoundException;
import k2.ania.ingredient_again.service.IngredientService;
import k2.ania.ingredient_again.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor

@RestController
public class IngredientController {
    private final IngredientService ingredientService;
    private final StockService stockService;


    @GetMapping("/ingredients")
    public ResponseEntity<?> findAll() {
        try {
            List<Ingredient> ingredients = ingredientService.findAll();
            return ResponseEntity
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(ingredients);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable("id") int id) {
        try {
            Ingredient ingredient = ingredientService.findIngredientById(id);
            return ResponseEntity
                    .status(200)
                    .header("Content-Type", "text/plain")
                    .body(ingredient);
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

}
