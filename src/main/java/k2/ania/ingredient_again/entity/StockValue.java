package k2.ania.ingredient_again.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StockValue {
    private double quantity;
    private DishIngredient.Unit unit;
}
