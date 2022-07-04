package poc.test.rest.person;

import org.mapstruct.Mapper;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;

@Mapper(componentModel = "spring")
public interface PersonCrudRestMapper {

    PersonToCreate toPersonToCreate(PersonCreateRequestPayload from);

    PersonReadResponsePayload toPersonReadResponsePayload(Person from);

}
