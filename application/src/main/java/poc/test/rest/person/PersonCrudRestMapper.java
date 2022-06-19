package poc.test.rest.person;

import org.mapstruct.Mapper;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

@Mapper(componentModel = "spring")
public interface PersonCrudRestMapper {

    PersonToCreate toPersonToCreate(PersonCreateRequestPayload from);

    PersonReadResponsePayload toPersonReadResponsePayload(Person from);

}
