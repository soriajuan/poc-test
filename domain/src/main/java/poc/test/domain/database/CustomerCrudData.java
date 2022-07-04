package poc.test.domain.database;

import lombok.NonNull;
import poc.test.domain.entities.CustomerToCreateUpdate;
import poc.test.domain.entities.CustomerWithOrders;

import java.util.UUID;

public interface CustomerCrudData {

    @NonNull
    CustomerWithOrders insert(@NonNull CustomerToCreateUpdate c);

    @NonNull
    CustomerWithOrders update(@NonNull UUID id, @NonNull CustomerToCreateUpdate c);

    void deleteById(@NonNull UUID id);

}
