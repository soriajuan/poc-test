package poc.test.domain;

import java.util.List;
import java.util.UUID;

public interface ReadPersonData {
    Person findById(UUID id);
    List<Person> findAll();
}
