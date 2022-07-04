package poc.test.data.person;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poc.test.data.config.SingletonPostgresContainer;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@SpringBootTest
class PersonCrudDataImplTest extends SingletonPostgresContainer {

    @Autowired
    PersonCrudDataImpl personCrudData;

    Person johnDoe = new Person(UUID.fromString("5dcdabc5-6d31-4281-8953-14b2ca3aac6b"), "John", "Doe");

    @Test
    @ExpectedDataSet(value = "person-crud/person-created.yml", ignoreCols = "id")
    void insert() {
        var toInsert = new PersonToCreate("John", "Doe");

        var inserted = personCrudData.insert(toInsert);

        assertNotNull(inserted);
        assertNotNull(inserted.id());

        assertEquals(toInsert.firstName(), inserted.firstName());
        assertEquals(toInsert.lastName(), inserted.lastName());
    }

    @Test
    @DataSet(value = "person-crud/all-persons.yml")
    void findAll() {
        var expected = List.of(
                johnDoe,
                new Person(UUID.fromString("4df83252-0f39-418c-a1b6-5c18ff62c346"), "Jane", "Doe")
        );
        var actual = personCrudData.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById_EntityNotFound() {
        var id = UUID.randomUUID();
        assertThatThrownBy(() -> personCrudData.findById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DataSet(value = "person-crud/john-doe.yml")
    void findById() {
        var actual = personCrudData.findById(johnDoe.id());
        assertEquals(johnDoe, actual);
    }

}
