package poc.test.rest.person;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonCrudRestMapperTest {

    PersonCrudRestMapper mapper = Mappers.getMapper(PersonCrudRestMapper.class);

    @Test
    void from_PersonCreateRequestPayload_to_PersonToCreate() {
        var string = "string";

        var from = new PersonCreateRequestPayload();
        from.setFirstName(string);
        from.setLastName(string);

        var expected = new PersonToCreate(string, string);

        var actual = mapper.toPersonToCreate(from);

        assertEquals(expected, actual);
    }

    @Test
    void from_Person_to_PersonReadResponsePayload() {
        var uuid = UUID.randomUUID();
        var string = "string";

        var from = new Person(uuid, string, string);

        var expected = new PersonReadResponsePayload();
        expected.setId(uuid);
        expected.setFirstName(string);
        expected.setLastName(string);

        var actual = mapper.toPersonReadResponsePayload(from);

        assertEquals(expected, actual);
    }

}
