package poc.test.data;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import poc.test.domain.Person;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonDataMapperTest {

    PersonDataMapper mapper = Mappers.getMapper(PersonDataMapper.class);

    @Test
    void fromPersonToPersonPsql() {
        var uuid = UUID.randomUUID();
        var string = "string";

        var from = Person.builder()
                .id(uuid)
                .firstName(string)
                .lastName(string)
                .build();

        var expected = new PersonPsql();
        expected.setId(uuid);
        expected.setFirstName(string);
        expected.setLastName(string);

        var actual = mapper.toPersonPsql(from);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    void fromPersonPsqlToPerson() {
        var uuid = UUID.randomUUID();
        var string = "string";

        var from = new PersonPsql();
        from.setId(uuid);
        from.setFirstName(string);
        from.setLastName(string);


        var expected = Person.builder()
                .id(uuid)
                .firstName(string)
                .lastName(string)
                .build();

        var actual = mapper.toPerson(from);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

}
