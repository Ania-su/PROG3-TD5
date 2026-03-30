package k2.ania.ingredient_again.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Order {

    public enum PaymentStatus {
        PAID, UNPAID
    }

    private int id;
    private String reference;
    private PaymentStatus paymentStatus;
    private Instant createdAt;
    private Sale sale;
    private List<DishOrder> orders;
}
