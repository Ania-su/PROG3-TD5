package k2.ania.ingredient_again.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Ingredient {

    public enum CategoryEnum {
        VEGETABLE,
        ANIMAL,
        MARINE,
        DIARY,
        OTHER
    }

    private int id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private List<StockMovement> stockMovementList;
}
