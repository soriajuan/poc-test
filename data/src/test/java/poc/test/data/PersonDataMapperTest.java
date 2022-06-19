package poc.test.data;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonDataMapperTest {

    PersonDataMapper mapper = Mappers.getMapper(PersonDataMapper.class);

    @Test
    void from_PersonToCreate_toPersonPsql() {
        var string = "string";

        var from = new PersonToCreate(string, string);

        var expected = new PersonPsql();
        expected.setFirstName(string);
        expected.setLastName(string);

        var actual = mapper.toPersonPsql(from);

        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    void fromPersonToPersonPsql() {
        var uuid = UUID.randomUUID();
        var string = "string";

        var from = new Person(uuid, string, string);

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


        var expected = new Person(uuid, string, string);

        var actual = mapper.toPerson(from);

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.firstName(), actual.firstName());
        assertEquals(expected.lastName(), actual.lastName());
    }

}
