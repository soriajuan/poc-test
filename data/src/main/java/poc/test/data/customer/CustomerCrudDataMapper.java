package poc.test.data.customer;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import poc.test.data.entities.CustomerPsql;
import poc.test.data.entities.OrderPsql;
import poc.test.domain.entities.CustomerToCreateUpdate;
import poc.test.domain.entities.CustomerWithOrders;
import poc.test.domain.entities.Order;

@Mapper(componentModel = "spring")
interface CustomerCrudDataMapper {

    @Mapping(target = "id", ignore = true)
    CustomerPsql toCustomerPsql(CustomerToCreateUpdate from);

    @Mapping(target = "customer.id", source = "customerId")
    OrderPsql toOrderPsql(Order from);

    @InheritInverseConfiguration
    Order toOrder(OrderPsql from);

    CustomerWithOrders toCustomerWithOrders(CustomerPsql from);

}
