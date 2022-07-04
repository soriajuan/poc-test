package poc.test.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.test.data.entities.OrderPsql;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderPsql, UUID> {
}
