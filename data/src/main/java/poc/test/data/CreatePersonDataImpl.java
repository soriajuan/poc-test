package poc.test.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poc.test.domain.CreatePersonData;
import poc.test.domain.Person;

@Component
@RequiredArgsConstructor
class CreatePersonDataImpl implements CreatePersonData {

    private final PersonDataMapper mapper;
    private final PersonJpaRepository jpaRepository;

    @Override
    public Person insert(Person p) {
        var toInsert = mapper.toPersonPsql(p);
        var inserted = jpaRepository.save(toInsert);
        return mapper.toPerson(inserted);
    }

}
