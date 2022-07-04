package poc.test.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public record Order(UUID id, UUID customerId, String description, BigDecimal total) {

    public Order(UUID id, String description, BigDecimal total) {
        this(id, null, description, total);
    }

    public Order(String description, BigDecimal total) {
        this(null, null, description, total);
    }

}
