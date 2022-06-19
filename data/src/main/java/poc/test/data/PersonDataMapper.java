package poc.test.data;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

@Mapper(componentModel = "spring")
interface PersonDataMapper {
    PersonPsql toPersonPsql(Person p);
    @Mapping(target = "id", ignore = true)
    PersonPsql toPersonPsql(PersonToCreate p);

    @InheritInverseConfiguration
    Person toPerson(PersonPsql p);
}
