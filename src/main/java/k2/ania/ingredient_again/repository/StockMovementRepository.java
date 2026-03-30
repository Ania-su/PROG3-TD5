package k2.ania.ingredient_again.repository;

import k2.ania.ingredient_again.entity.DishIngredient;
import k2.ania.ingredient_again.entity.StockValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;

@AllArgsConstructor
@Repository
public class StockMovementRepository {
    private final DataSource dataSource;

    public StockValue findIngredientById(int id, Instant date, DishIngredient.Unit unit) {
        StockValue stockValue = new StockValue();

        String sql = """
                select id_ingredient,unit,
                    sum(case
                        when type = 'IN' then quantity
                        when type = 'OUT' then -quantity
                        else 0
                        end) as quant
                from stockmovement
                where id_ingredient = ?
                and creation_datetime <= ?
                and unit = ?::unit
                group by id_ingredient, unit
                """;
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                stockValue.setQuantity(rs.getDouble("quant"));
                stockValue.setUnit(DishIngredient.Unit.valueOf(rs.getString("unit")));
            }
            return stockValue;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
