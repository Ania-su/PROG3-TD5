package k2.ania.ingredient_again.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Dish {

    public enum DishType {
        START, MAIN, DESSERT
    }

    private int id;
    private String name;
    private DishType dishType;
    private Double sellingPrice;
    private List<DishIngredient> ingredients;
}
