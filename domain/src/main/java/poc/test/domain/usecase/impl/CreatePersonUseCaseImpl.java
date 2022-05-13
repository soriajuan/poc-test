package poc.test.domain.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import poc.test.domain.Person;
import poc.test.domain.PersonData;
import poc.test.domain.usecase.CreatePersonUseCase;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class CreatePersonUseCaseImpl implements CreatePersonUseCase {

    private final PersonData personData;

    @Override
    public Person create(Person p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException("person object cannot be null");
        }
        if (StringUtils.isBlank(p.getFirstName())) {
            throw new IllegalArgumentException("first name cannot be blank");
        }
        if (StringUtils.isBlank(p.getLastName())) {
            throw new IllegalArgumentException("last name cannot be blank");
        }
        return personData.insert(p);
    }

}
