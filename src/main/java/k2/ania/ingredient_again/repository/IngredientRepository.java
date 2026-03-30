package k2.ania.ingredient_again.repository;

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
public class IngredientRepository {
    private final DataSource dataSource;
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();

        String sql = "select id, name, price, category from ingredient";

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setPrice(rs.getDouble("price"));
                ingredient.setCategory(Ingredient.CategoryEnum.valueOf(rs.getString("category")));
                ingredients.add(ingredient);
            }

            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Ingredient findIngredientById(int id) {

        Ingredient ingredient = new Ingredient();
        String sql = "select id, name, price, category from ingredient where id = ?";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setPrice(rs.getDouble("price"));
                ingredient.setCategory(Ingredient.CategoryEnum.valueOf(rs.getString("category")));
            }
            return ingredient;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
