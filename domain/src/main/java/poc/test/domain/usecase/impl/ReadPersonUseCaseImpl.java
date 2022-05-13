package poc.test.domain.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poc.test.domain.Person;
import poc.test.domain.PersonData;
import poc.test.domain.usecase.ReadPersonUseCase;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ReadPersonUseCaseImpl implements ReadPersonUseCase {

    private final PersonData personData;

    @Override
    public Person readById(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("person id cannot be null");
        }
        return personData.findById(id);
    }

    @Override
    public List<Person> readAll() {
        return personData.findAll();
    }

}
