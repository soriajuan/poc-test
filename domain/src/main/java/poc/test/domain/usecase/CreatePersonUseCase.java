package poc.test.domain.usecase;

import lombok.NonNull;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

public interface CreatePersonUseCase {
    Person create(@NonNull PersonToCreate p);
}
