package poc.test.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface PersonJpaRepository extends JpaRepository<PersonPsql, UUID> {
}
