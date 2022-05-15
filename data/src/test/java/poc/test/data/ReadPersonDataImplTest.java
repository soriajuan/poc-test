package poc.test.data;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poc.test.domain.EntityNotFoundException;
import poc.test.domain.Person;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DBRider
@SpringBootTest
class ReadPersonDataImplTest extends PostgreSQLContainerConfiguration {

    @Autowired
    ReadPersonDataImpl readPersonData;

    Person johnDoe = Person.builder()
            .id(UUID.fromString("5dcdabc5-6d31-4281-8953-14b2ca3aac6b"))
            .firstName("John")
            .lastName("Doe")
            .build();

    @Test
    @DataSet(value = "read-person/all-persons.yml")
    void findAll() {
        var expected = List.of(
                johnDoe,
                Person.builder().id(UUID.fromString("4df83252-0f39-418c-a1b6-5c18ff62c346")).firstName("Jane").lastName("Doe").build()
        );
        var actual = readPersonData.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById_EntityNotFound() {
        var id = UUID.randomUUID();
        assertThatThrownBy(() -> readPersonData.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("entity person not found under id " + id);
    }

    @Test
    @DataSet(value = "read-person/one-person.yml")
    void findById() {
        var actual = readPersonData.findById(johnDoe.getId());
        assertEquals(johnDoe, actual);
    }

}
