package poc.test.data;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import poc.test.data.config.SingletonPostgresContainer;
import poc.test.domain.PersonToCreate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DBRider
@SpringBootTest
class CreatePersonDataImplTest extends SingletonPostgresContainer {

    @Autowired
    CreatePersonDataImpl createPersonData;

    @Test
    @ExpectedDataSet(value = "create-person/person-created.yml", ignoreCols = "id")
    void insert() {
        var toInsert = new PersonToCreate("John", "Doe");

        var inserted = createPersonData.insert(toInsert);

        assertNotNull(inserted);
        assertNotNull(inserted.id());

        assertEquals(toInsert.firstName(), inserted.firstName());
        assertEquals(toInsert.lastName(), inserted.lastName());
    }

}
