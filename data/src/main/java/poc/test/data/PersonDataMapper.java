package poc.test.data;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import poc.test.domain.Person;

@Mapper(componentModel = "spring")
public interface PersonDataMapper {
    PersonPsql toPersonPsql(Person p);

    @InheritInverseConfiguration
    Person toPerson(PersonPsql p);
}
