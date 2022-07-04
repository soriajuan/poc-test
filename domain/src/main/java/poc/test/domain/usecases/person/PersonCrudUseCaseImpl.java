package poc.test.domain.usecases.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poc.test.domain.database.PersonCrudData;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;
import poc.test.domain.services.PersonCrudUseCase;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class PersonCrudUseCaseImpl implements PersonCrudUseCase {

    private final PersonCrudData personCrudData;

    @Override
    public Person create(@NonNull PersonToCreate p) {
        validateFieldNotEmpty(p.firstName(), "firstName");
        validateFieldNotEmpty(p.lastName(), "lastName");
        return personCrudData.insert(p);
    }

    @Override
    public Person readById(@NonNull UUID id) {
        return personCrudData.findById(id);
    }

    @Override
    public @NonNull List<Person> readAll() {
        return personCrudData.findAll();
    }

    private void validateFieldNotEmpty(@NonNull String field, @NonNull String fieldName) {
        if (field.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s cannot be empty", fieldName));
        }
    }

}
