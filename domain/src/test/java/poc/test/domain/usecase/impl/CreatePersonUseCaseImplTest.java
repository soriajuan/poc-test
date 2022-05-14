package poc.test.domain.usecase.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poc.test.domain.CreatePersonData;
import poc.test.domain.Person;

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
    void create_personObjectValidation() {
        assertThatThrownBy(() -> service.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("person object cannot be null");

        verifyNoInteractions(data);
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "create_firstNameValidation - {argumentsWithNames}")
    void create_firstNameValidation(String firstName) {
        var person = Person.builder().firstName(firstName).build();
        assertThatThrownBy(() -> service.create(person))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("first name cannot be blank");

        verifyNoInteractions(data);
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "create_lastNameValidation - {argumentsWithNames}")
    void create_lastNameValidation(String lastName) {
        var person = Person.builder().firstName("firstName").lastName(lastName).build();
        assertThatThrownBy(() -> service.create(person))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("last name cannot be blank");

        verifyNoInteractions(data);
    }

    @Test
    void create() {
        var person = Person.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var expected = person.toBuilder().id(UUID.randomUUID()).build();
        when(data.insert(any(Person.class)))
                .thenReturn(expected);

        var actual = service.create(person);

        assertEquals(expected, actual);

        verify(data).insert(any(Person.class));
        verifyNoMoreInteractions(data);
    }

}
