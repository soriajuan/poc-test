package poc.test.domain.entities;

import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record CustomerToCreateUpdate(@NonNull String name, List<Order> orders) {

    public CustomerToCreateUpdate(@NonNull String name, List<Order> orders) {
        this.name = name;
        this.orders = Objects.nonNull(orders) ? orders : Collections.emptyList();
    }

    public CustomerToCreateUpdate(@NonNull String name) {
        this(name, null);
    }

}
