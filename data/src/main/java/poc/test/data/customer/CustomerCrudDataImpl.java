package poc.test.data.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poc.test.data.repositories.CustomerJpaRepository;
import poc.test.domain.database.CustomerCrudData;
import poc.test.domain.entities.CustomerToCreateUpdate;
import poc.test.domain.entities.CustomerWithOrders;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class CustomerCrudDataImpl implements CustomerCrudData {

    private final CustomerCrudDataMapper mapper;
    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public @NonNull CustomerWithOrders insert(@NonNull CustomerToCreateUpdate c) {
        var customerPsql = mapper.toCustomerPsql(c);
        customerPsql.getOrders().forEach(orderPsql -> orderPsql.setCustomer(customerPsql));
        var inserted = customerJpaRepository.save(customerPsql);
        return mapper.toCustomerWithOrders(inserted);
    }

    @Override
    public @NonNull CustomerWithOrders update(@NonNull UUID id, @NonNull CustomerToCreateUpdate c) {
        var customerPsql = mapper.toCustomerPsql(c);
        customerPsql.setId(id);
        customerPsql.getOrders().forEach(orderPsql -> orderPsql.setCustomer(customerPsql));
        var updated = customerJpaRepository.save(customerPsql);
        return mapper.toCustomerWithOrders(updated);
    }

    @Override
    public void deleteById(@NonNull UUID id) {
        customerJpaRepository.deleteById(id);
    }

}
