package poc.test.domain.usecases.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poc.test.domain.database.PersonCrudData;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;
import poc.test.domain.exceptions.EntityNotFoundException;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonCrudUseCaseImplTest {

    @Mock
    PersonCrudData personCrudData;

    @InjectMocks
    PersonCrudUseCaseImpl service;

    @Test
    void create_validateFirstNameAndLastNameAreNotEmpty() {
        var emptyFirstName = new PersonToCreate("", "lastName");
        assertThatThrownBy(() -> service.create(emptyFirstName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("firstName cannot be empty");

        var emptyLastName = new PersonToCreate("firstName", "");
        assertThatThrownBy(() -> service.create(emptyLastName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lastName cannot be empty");

        verifyNoInteractions(personCrudData);
    }

    @Test
    void create() {
        var person = new PersonToCreate("firstName", "lastName");

        var expected = new Person(UUID.randomUUID(), "firstName", "lastName");
        when(personCrudData.insert(any(PersonToCreate.class)))
                .thenReturn(expected);

        var actual = service.create(person);

        assertEquals(expected, actual);

        verify(personCrudData).insert(any(PersonToCreate.class));
        verifyNoMoreInteractions(personCrudData);
    }

    @Test
    void readById_EntityNotFoundException() {
        var uuid = UUID.randomUUID();

        when(personCrudData.findById(any(UUID.class)))
                .thenThrow(new EntityNotFoundException(String.format("entity person not found under id %s", uuid)));

        assertThatThrownBy(() -> service.readById(uuid))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("entity person not found under id " + uuid);

        verify(personCrudData).findById(any(UUID.class));
        verifyNoMoreInteractions(personCrudData);
    }

    @Test
    void readById() {
        var uuid = UUID.randomUUID();

        var expected = new Person(uuid, "firstName", "lastName");

        when(personCrudData.findById(any(UUID.class)))
                .thenReturn(expected);

        var actual = service.readById(uuid);

        assertEquals(expected, actual);

        verify(personCrudData).findById(any(UUID.class));
        verifyNoMoreInteractions(personCrudData);
    }

    @Test
    void readAll() {
        when(personCrudData.findAll())
                .thenReturn(Collections.emptyList());

        var actual = service.readAll();

        assertEquals(Collections.emptyList(), actual);

        verify(personCrudData).findAll();
        verifyNoMoreInteractions(personCrudData);
    }

}
