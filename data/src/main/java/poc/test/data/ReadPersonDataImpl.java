package poc.test.data;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poc.test.domain.EntityNotFoundException;
import poc.test.domain.Person;
import poc.test.domain.ReadPersonData;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ReadPersonDataImpl implements ReadPersonData {

    private final PersonDataMapper mapper;
    private final PersonJpaRepository jpaRepository;

    @Override
    public Person findById(@NonNull UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toPerson)
                .orElseThrow(() -> new EntityNotFoundException(String.format("entity person not found under id %s", id)));
    }

    @Override
    public List<Person> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toPerson)
                .toList();
    }

}
