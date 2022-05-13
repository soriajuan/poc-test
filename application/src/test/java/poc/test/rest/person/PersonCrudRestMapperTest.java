package poc.test.rest.person;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import poc.test.domain.Person;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonCrudRestMapperTest {

    PersonCrudRestMapper mapper = Mappers.getMapper(PersonCrudRestMapper.class);

    @Test
    void from_PersonCreateRequestPayload_to_Person() {
        var string = "string";

        var from = new PersonCreateRequestPayload();
        from.setFirstName(string);
        from.setLastName(string);

        var expected = Person.builder()
                .firstName(string)
                .lastName(string)
                .build();

        var actual = mapper.toPerson(from);

        assertEquals(expected, actual);
    }

    @Test
    void from_Person_to_PersonReadResponsePayload() {
        var uuid = UUID.randomUUID();
        var string = "string";

        var from = Person.builder()
                .id(uuid)
                .firstName(string)
                .lastName(string)
                .build();

        var expected = new PersonReadResponsePayload();
        expected.setId(uuid);
        expected.setFirstName(string);
        expected.setLastName(string);

        var actual = mapper.toPersonReadResponsePayload(from);

        assertEquals(expected, actual);
    }

}
