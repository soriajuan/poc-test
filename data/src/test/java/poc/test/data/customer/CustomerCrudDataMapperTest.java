package poc.test.data.customer;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import poc.test.data.entities.CustomerPsql;
import poc.test.data.entities.OrderPsql;
import poc.test.domain.entities.CustomerToCreateUpdate;
import poc.test.domain.entities.CustomerWithOrders;
import poc.test.domain.entities.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerCrudDataMapperTest {

    CustomerCrudDataMapper mapper = Mappers.getMapper(CustomerCrudDataMapper.class);

    private static final UUID UUID_VALUE = UUID.randomUUID();
    private static final String STRING_VALUE = "string";
    private static final BigDecimal BIG_DECIMAL_VALUE = BigDecimal.TEN;

    @Test
    void from_CustomerToCreateUpdate_to_CustomerPsql() {
        var from = new CustomerToCreateUpdate(STRING_VALUE, List.of(new Order(UUID_VALUE, UUID_VALUE, STRING_VALUE, BIG_DECIMAL_VALUE)));

        var actual = mapper.toCustomerPsql(from);

        assertNotNull(actual);
        assertEquals(STRING_VALUE, actual.getName());

        var orders = actual.getOrders();
        assertNotNull(orders);
        assertEquals(1, orders.size());

        var order = orders.get(0);
        assertNotNull(order);
        assertEquals(UUID_VALUE, order.getId());

        assertNotNull(order.getCustomer());
        assertEquals(UUID_VALUE, order.getCustomer().getId());

        assertEquals(STRING_VALUE, order.getDescription());
        assertEquals(BIG_DECIMAL_VALUE, order.getTotal());
    }

    @Test
    void from_Order_to_OrderPsql() {
        var from = new Order(UUID_VALUE, UUID_VALUE, STRING_VALUE, BIG_DECIMAL_VALUE);

        var actual = mapper.toOrderPsql(from);

        assertNotNull(actual);
        assertEquals(UUID_VALUE, actual.getId());

        var customer = actual.getCustomer();
        assertNotNull(customer);
        assertEquals(UUID_VALUE, customer.getId());

        assertEquals(STRING_VALUE, actual.getDescription());
        assertEquals(BIG_DECIMAL_VALUE, actual.getTotal());
    }

    @Test
    void from_OrderPsql_to_Order() {
        var from = new OrderPsql(UUID_VALUE, new CustomerPsql(UUID_VALUE), STRING_VALUE, BIG_DECIMAL_VALUE);

        var actual = mapper.toOrder(from);

        assertNotNull(actual);
        assertEquals(UUID_VALUE, actual.id());
        assertEquals(UUID_VALUE, actual.customerId());
        assertEquals(STRING_VALUE, actual.description());
        assertEquals(BIG_DECIMAL_VALUE, actual.total());
    }

    @Test
    void from_CustomerPsql_to_CustomerWithOrders() {
        var orderPsql = new OrderPsql(UUID_VALUE, new CustomerPsql(UUID_VALUE), STRING_VALUE, BIG_DECIMAL_VALUE);
        var from = new CustomerPsql(UUID_VALUE, STRING_VALUE, List.of(orderPsql));

        var order = new Order(UUID_VALUE, UUID_VALUE, STRING_VALUE, BIG_DECIMAL_VALUE);
        var expected = new CustomerWithOrders(UUID_VALUE, STRING_VALUE, List.of(order));

        var actual = mapper.toCustomerWithOrders(from);

        assertEquals(expected, actual);
    }

}
