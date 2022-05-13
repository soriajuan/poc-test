package poc.test.domain.usecase;

import poc.test.domain.Person;

import java.util.List;
import java.util.UUID;

public interface ReadPersonUseCase {
    Person readById(UUID id);
    List<Person> readAll();
}
