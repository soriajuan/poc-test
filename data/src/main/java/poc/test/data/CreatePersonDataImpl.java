package poc.test.data;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poc.test.domain.CreatePersonData;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

@Component
@RequiredArgsConstructor
class CreatePersonDataImpl implements CreatePersonData {

    private final PersonDataMapper mapper;
    private final PersonJpaRepository jpaRepository;

    @Override
    public Person insert(@NonNull PersonToCreate p) {
        var toInsert = mapper.toPersonPsql(p);
        var inserted = jpaRepository.save(toInsert);
        return mapper.toPerson(inserted);
    }

}
