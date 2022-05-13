package poc.test.rest.person;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import poc.test.domain.Person;

@Mapper(componentModel = "spring")
public interface PersonCrudRestMapper {

    @Mapping(target = "id", ignore = true)
    Person toPerson(PersonCreateRequestPayload from);

    PersonReadResponsePayload toPersonReadResponsePayload(Person from);

}
