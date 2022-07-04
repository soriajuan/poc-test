package poc.test.domain.services;

import lombok.NonNull;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;

import java.util.List;
import java.util.UUID;

public interface PersonCrudUseCase {

    Person create(@NonNull PersonToCreate p);

    Person readById(@NonNull UUID id);

    @NonNull
    List<Person> readAll();

}
