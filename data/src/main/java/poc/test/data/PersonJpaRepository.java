package poc.test.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * TODO check why package private fails to be initialized in a multi-module project
 */
public interface PersonJpaRepository extends JpaRepository<PersonPsql, UUID> {
}
