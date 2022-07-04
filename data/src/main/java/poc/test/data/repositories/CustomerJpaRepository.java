package poc.test.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.test.data.entities.CustomerPsql;

import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<CustomerPsql, UUID> {
}
