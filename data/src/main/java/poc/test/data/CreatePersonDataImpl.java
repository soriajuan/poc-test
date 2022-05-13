package poc.test.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poc.test.domain.Person;
import poc.test.domain.PersonData;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class CreatePersonDataImpl implements PersonData {

    private final PersonDataMapper mapper;
    private final PersonJpaRepository jpaRepository;

    @Override
    public Person insert(Person p) {
        var toInsert = mapper.toPersonPsql(p);
        var inserted = jpaRepository.save(toInsert);
        return mapper.toPerson(inserted);
    }

    @Override
    public Person findById(UUID ui) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return Collections.emptyList();
    }

}
