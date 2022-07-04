package poc.test.domain.database;

import lombok.NonNull;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;

import java.util.List;
import java.util.UUID;

public interface PersonCrudData {

    @NonNull
    Person insert(@NonNull PersonToCreate p);

    @NonNull
    Person findById(@NonNull UUID id);

    @NonNull
    List<Person> findAll();

}
