package k2.ania.ingredient_again.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Sale {
    private int id;
    private Instant createdAt;
    private Order order;
}
