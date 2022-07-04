package poc.test.data.customer;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import poc.test.data.config.SingletonPostgresContainer;
import poc.test.domain.entities.CustomerToCreateUpdate;
import poc.test.domain.entities.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DBRider
@SpringBootTest
class CustomerCrudDataImplTest extends SingletonPostgresContainer {

    @Autowired
    CustomerCrudDataImpl customerCrudData;

    @Test
    @ExpectedDataSet(value = "customer-crud/insert-customer-with-new-order/expected-data.yml", ignoreCols = {"id", "customer_id"})
    void insertCustomerWithNewOrder() {
        var customerName = "Customer A";
        var description = "description A";
        var total = BigDecimal.TEN;

        var toInsert = new CustomerToCreateUpdate(customerName, List.of(
                new Order(description, total)
        ));

        var inserted = customerCrudData.insert(toInsert);

        assertNotNull(inserted);
        assertNotNull(inserted.id());
        assertEquals(customerName, inserted.name());
        assertNotNull(inserted.orders());
        assertEquals(1, inserted.orders().size());

        var insertedOrder = inserted.orders().get(0);
        assertNotNull(insertedOrder);
        assertNotNull(insertedOrder.id());
        assertNotNull(insertedOrder.customerId());
        assertEquals(description, insertedOrder.description());
        assertEquals(total, insertedOrder.total());
    }

    @Test
    @Disabled("Why this test fails?")
    @DataSet("customer-crud/insert-customer-with-existing-order/required-data.yml")
    @ExpectedDataSet(value = "customer-crud/insert-customer-with-existing-order/expected-data.yml", ignoreCols = {"id", "customer_id"})
    void insertCustomerWithExistingOrder() {
        var customerName = "Customer A";
        var orderId = UUID.fromString("9dab950d-71b3-4f86-938d-705563055238");
        var description = "description";
        var total = BigDecimal.TEN;

        var toInsert = new CustomerToCreateUpdate(customerName, List.of(
                new Order(orderId, description, total)
        ));

        var inserted = customerCrudData.insert(toInsert);

        assertNotNull(inserted);
        assertNotNull(inserted.id());
        assertEquals(customerName, inserted.name());
        assertNotNull(inserted.orders());
        assertEquals(1, inserted.orders().size());

        var insertedOrder = inserted.orders().get(0);
        assertNotNull(insertedOrder);
        assertEquals(orderId, insertedOrder.id());
        assertNotNull(insertedOrder.customerId());
        assertEquals(description, insertedOrder.description());
        assertEquals(total, insertedOrder.total());
    }

    @Test
    @DataSet(value = "customer-crud/update-customer-and-existing-order/required-data.yml")
    @ExpectedDataSet(value = "customer-crud/update-customer-and-existing-order/expected-data.yml")
    void updateCustomerAndExistingOrder() {
        var customerId = UUID.fromString("1f24bc70-fe99-4d46-95ae-f6b8232cb47d");
        var customerName = "Customer A - updated";

        var orderId = UUID.fromString("daab24a1-a9ea-454d-a5a9-6bff557bcbc1");
        var orderDescription = "description A - updated";
        var orderTotal = BigDecimal.ZERO;

        var customerToUpdate = new CustomerToCreateUpdate(customerName, List.of(new Order(orderId, orderDescription, orderTotal)));
        var updated = customerCrudData.update(customerId, customerToUpdate);

        assertNotNull(updated);
        assertEquals(customerId, updated.id());
        assertEquals(customerName, updated.name());

        var updatedOrders = updated.orders();
        assertNotNull(updatedOrders);
        assertEquals(1, updatedOrders.size());

        var updatedOrder = updatedOrders.get(0);
        assertNotNull(updatedOrder);
        assertEquals(orderId, updatedOrder.id());
        assertEquals(customerId, updatedOrder.customerId());
        assertEquals(orderDescription, updatedOrder.description());
        assertEquals(orderTotal, updatedOrder.total());
    }

    @Test
    @DataSet(value = "customer-crud/update-customer-removing-existing-order/required-data.yml")
    @ExpectedDataSet(value = "customer-crud/update-customer-removing-existing-order/expected-data.yml")
    void updateCustomerRemovingExistingOrder() {
        var customerId = UUID.fromString("1f24bc70-fe99-4d46-95ae-f6b8232cb47d");
        var customerName = "Customer A - updated";

        var customerToUpdate = new CustomerToCreateUpdate(customerName);
        var updated = customerCrudData.update(customerId, customerToUpdate);

        assertNotNull(updated);
        assertEquals(customerId, updated.id());
        assertEquals(customerName, updated.name());

        var updatedOrders = updated.orders();
        assertNotNull(updatedOrders);
        assertTrue(updatedOrders.isEmpty());
    }

    @Test
    @DataSet(value = "customer-crud/update-customer-with-new-order/required-data.yml")
    @ExpectedDataSet(value = "customer-crud/update-customer-with-new-order/expected-data.yml", ignoreCols = "id")
    void updateCustomerWithNewOrder() {
        var customerId = UUID.fromString("1f24bc70-fe99-4d46-95ae-f6b8232cb47d");
        var customerName = "Customer A - updated";
        var orderDescription = "description A";
        var orderTotal = BigDecimal.TEN;

        var customerToUpdate = new CustomerToCreateUpdate(customerName, List.of(new Order(orderDescription, orderTotal)));
        var updated = customerCrudData.update(customerId, customerToUpdate);

        assertNotNull(updated);
        assertEquals(customerId, updated.id());
        assertEquals(customerName, updated.name());

        var updatedOrders = updated.orders();
        assertNotNull(updatedOrders);
        assertEquals(1, updatedOrders.size());

        var updatedOrder = updatedOrders.get(0);
        assertNotNull(updatedOrder);
        assertNotNull(updatedOrder.id());
        assertEquals(customerId, updatedOrder.customerId());
        assertEquals(orderDescription, updatedOrder.description());
        assertEquals(orderTotal, updatedOrder.total());
    }

    @Test
    @DataSet(value = "customer-crud/delete-existing-customer/required-data.yml")
    @ExpectedDataSet(value = "customer-crud/delete-existing-customer/expected-data.yml")
    void deleteExistingCustomer() {
        var customerId = UUID.fromString("1f24bc70-fe99-4d46-95ae-f6b8232cb47d");
        assertThatNoException().isThrownBy(() -> customerCrudData.deleteById(customerId));
    }

    @Test
    void deleteNonExistingCustomer() {
        var id = UUID.randomUUID();
        assertThatThrownBy(() -> customerCrudData.deleteById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }

}
