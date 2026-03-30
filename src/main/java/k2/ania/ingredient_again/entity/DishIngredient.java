package k2.ania.ingredient_again.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DishIngredient {

    public enum Unit {
        PCS, KG, L
    }

    private int id;
    private Dish dish;
    private Ingredient ingredient;
    private Double quantity_required;
    private Unit unit;
}
