package k2.ania.ingredient_again.repository;

import k2.ania.ingredient_again.entity.Dish;
import k2.ania.ingredient_again.entity.DishIngredient;
import k2.ania.ingredient_again.entity.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@Repository
public class DishRepository {
    private final DataSource dataSource;

    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        String sql = """
                select
                    id,
                    name,
                    dish_type,
                    selling_price
                from dish
        """;

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setSellingPrice(rs.getDouble("selling_price"));
                dish.setIngredients(findIngredientByDishId(dish.getId()));
                dishes.add(dish);
            }

            return dishes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dish findDishById(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select
                                dish.id as dish_id,
                                dish.name as dish_name,
                                dish_type,
                                dish.selling_price as dish_price
                            from dish
                            where dish.id = ?;
                            """);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("dish_id"));
                dish.setName(resultSet.getString("dish_name"));
                dish.setDishType(Dish.DishType.valueOf(resultSet.getString("dish_type")));
                dish.setSellingPrice(resultSet.getObject("dish_price") == null
                        ? null : resultSet.getDouble("dish_price"));
                dish.setIngredients(findIngredientByDishId(id));
                return dish;
            }

            connection.close();
            throw new RuntimeException("Dish not found " + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DishIngredient> findIngredientByDishId(Integer idDish) throws SQLException {

        Connection connection = dataSource.getConnection();
        List<DishIngredient> dishIngredients = new ArrayList<>();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select
                                ingredient.id,
                                ingredient.name,
                                ingredient.price,
                                ingredient.category,
                                di.quantity_required,
                                di.unit_type
                            from ingredient
                            join dishingredient di
                            on di.id_ingredient = ingredient.id
                            where id_dish = ?;
                            """);

            preparedStatement.setInt(1, idDish);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(Ingredient.CategoryEnum.valueOf(resultSet.getString("category")));

                DishIngredient dishIngredient = new DishIngredient();
                dishIngredient.setIngredient(ingredient);
                dishIngredient.setQuantity_required(resultSet.getObject("quantity_required") == null ? null : resultSet.getDouble("quantity_required"));
                dishIngredient.setUnit(DishIngredient.Unit.valueOf(resultSet.getString("unit_type")));

                dishIngredients.add(dishIngredient);
            }

            connection.close();
            return dishIngredients;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void detachIngredient (Integer dishId, Integer ingredientId) throws SQLException {
        String sql = """
            delete from dishingredient
            where dish.id = ?
            and ingredient.id = ?;
        """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dishId);
            ps.setInt(2, ingredientId);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void attachIngredient(Integer dishId, Integer ingredientId) throws SQLException {
        String sql = """
            insert into dishingredient(
                id_dish, id_ingredient, quantity_required, unit_type
            )
            values (?, ?, ?, ?::unit_type);
        """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dishId);
            ps.setInt(2, ingredientId);
            ps.setDouble(3, 0.0);
            ps.setString(4, "unit_type");

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

}
