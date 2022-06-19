package poc.test.domain.usecase.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poc.test.domain.CreatePersonData;
import poc.test.domain.Person;
import poc.test.domain.PersonToCreate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePersonUseCaseImplTest {

    @Mock
    CreatePersonData data;

    @InjectMocks
    CreatePersonUseCaseImpl service;

    @Test
    void create_validateFirstNameAndLastNameAreNotEmpty() {
        var emptyFirstName = new PersonToCreate("", "lastName");
        assertThatThrownBy(() -> service.create(emptyFirstName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("first name cannot be empty");

        var emptyLastName = new PersonToCreate("firstName", "");
        assertThatThrownBy(() -> service.create(emptyLastName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("last name cannot be empty");

        verifyNoInteractions(data);
    }

    @Test
    void create() {
        var person = new PersonToCreate("firstName", "lastName");

        var expected = new Person(UUID.randomUUID(), "firstName", "lastName");
        when(data.insert(any(PersonToCreate.class)))
                .thenReturn(expected);

        var actual = service.create(person);

        assertEquals(expected, actual);

        verify(data).insert(any(PersonToCreate.class));
        verifyNoMoreInteractions(data);
    }

}
