package poc.test.domain.usecase.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poc.test.domain.CreatePersonData;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;
import poc.test.domain.usecase.CreatePersonUseCase;

@Service
@RequiredArgsConstructor
class CreatePersonUseCaseImpl implements CreatePersonUseCase {

    private final CreatePersonData createPersonData;

    @Override
    public Person create(@NonNull PersonToCreate p) {
        validateFieldNotEmpty(p.firstName(), "first name");
        validateFieldNotEmpty(p.lastName(), "last name");
        return createPersonData.insert(p);
    }

    private void validateFieldNotEmpty(@NonNull String field, @NonNull String fieldName) {
        if (field.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s cannot be empty", fieldName));
        }
    }

}
