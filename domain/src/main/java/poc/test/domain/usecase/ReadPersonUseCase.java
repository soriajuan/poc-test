package poc.test.domain.usecase;

import lombok.NonNull;
import poc.test.domain.Person;

import java.util.List;
import java.util.UUID;

public interface ReadPersonUseCase {
    Person readById(@NonNull UUID id);
    List<Person> readAll();
}
