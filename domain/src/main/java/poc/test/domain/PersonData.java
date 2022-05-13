package poc.test.domain;

import java.util.List;
import java.util.UUID;

public interface PersonData {
    Person insert(Person p);
    Person findById(UUID ui);
    List<Person> findAll();
}
