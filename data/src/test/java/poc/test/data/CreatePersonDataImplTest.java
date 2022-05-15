package poc.test.data;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poc.test.domain.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@SpringBootTest
class CreatePersonDataImplTest extends PostgreSQLContainerConfiguration {

    @Autowired
    CreatePersonDataImpl createPersonData;

    @Test
    @ExpectedDataSet(value = "create-person/person-created.yml", ignoreCols = "id")
    void insert() {
        var toInsert = Person.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        var inserted = createPersonData.insert(toInsert);

        assertNotNull(inserted);
        assertNotNull(inserted.getId());

        assertEquals(toInsert.getFirstName(), inserted.getFirstName());
        assertEquals(toInsert.getLastName(), inserted.getLastName());
    }

}
