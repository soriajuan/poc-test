package poc.test.domain.entities;

import java.util.List;
import java.util.UUID;

public record CustomerWithOrders(UUID id, String name, List<Order> orders) {
}
