package poc.test.data.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import poc.test.data.repositories.PersonJpaRepository;
import poc.test.domain.database.PersonCrudData;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class PersonCrudDataImpl implements PersonCrudData {

    private final PersonCrudDataMapper mapper;
    private final PersonJpaRepository jpaRepository;

    @Override
    public @NonNull Person insert(@NonNull PersonToCreate p) {
        var toInsert = mapper.toPersonPsql(p);
        var inserted = jpaRepository.save(toInsert);
        return mapper.toPerson(inserted);
    }

    @Override
    @Transactional
    public @NonNull Person findById(@NonNull UUID id) {
        var personPsql = jpaRepository.getReferenceById(id);
        return mapper.toPerson(personPsql);
    }

    @Override
    public @NonNull List<Person> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toPerson)
                .toList();
    }

}
