package poc.test.data.person;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import poc.test.data.entities.PersonPsql;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;

@Mapper(componentModel = "spring")
interface PersonCrudDataMapper {
    PersonPsql toPersonPsql(Person p);
    @Mapping(target = "id", ignore = true)
    PersonPsql toPersonPsql(PersonToCreate p);

    @InheritInverseConfiguration
    Person toPerson(PersonPsql p);
}
