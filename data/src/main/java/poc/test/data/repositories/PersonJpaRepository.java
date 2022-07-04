package poc.test.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.test.data.entities.PersonPsql;

import java.util.UUID;

public interface PersonJpaRepository extends JpaRepository<PersonPsql, UUID> {
}
