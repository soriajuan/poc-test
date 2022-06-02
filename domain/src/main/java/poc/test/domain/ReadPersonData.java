package poc.test.domain;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface ReadPersonData {
    Person findById(@NonNull UUID id);
    List<Person> findAll();
}
