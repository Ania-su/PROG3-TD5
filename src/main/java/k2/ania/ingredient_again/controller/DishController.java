package k2.ania.ingredient_again.controller;

import k2.ania.ingredient_again.entity.Ingredient;
import k2.ania.ingredient_again.exceptions.NotFoundException;
import k2.ania.ingredient_again.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor

@RestController
public class DishController {
    private final DishService dishService;

    @GetMapping("/dishes")
    public ResponseEntity<?> findAll() throws SQLException {
        try{
            return ResponseEntity
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(dishService.findAll());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) throws SQLException {
        try {
            return ResponseEntity
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(dishService.findDishById(id));
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }

    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<?> attachOrDetachIngredient(@PathVariable("id") int id, @RequestBody List<Ingredient> ingredients) throws SQLException {
        try {
            return ResponseEntity
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(dishService.attachAndDetachIngredient(id, ingredients));
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        }
    }
}
