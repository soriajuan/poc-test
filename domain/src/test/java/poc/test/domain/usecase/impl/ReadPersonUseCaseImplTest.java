package poc.test.domain.usecase.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poc.test.domain.EntityNotFoundException;
import poc.test.domain.Person;
import poc.test.domain.ReadPersonData;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadPersonUseCaseImplTest {

    @Mock
    ReadPersonData data;

    @InjectMocks
    ReadPersonUseCaseImpl service;

    @Test
    void readById_EntityNotFoundException() {
        var uuid = UUID.randomUUID();

        when(data.findById(any(UUID.class)))
                .thenThrow(new EntityNotFoundException(String.format("entity person not found under id %s", uuid)));

        assertThatThrownBy(() -> service.readById(uuid))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("entity person not found under id " + uuid);

        verify(data).findById(any(UUID.class));
        verifyNoMoreInteractions(data);
    }

    @Test
    void readById() {
        var uuid = UUID.randomUUID();

        var expected = new Person(uuid, "firstName", "lastName");

        when(data.findById(any(UUID.class)))
                .thenReturn(expected);

        var actual = service.readById(uuid);

        assertEquals(expected, actual);

        verify(data).findById(any(UUID.class));
        verifyNoMoreInteractions(data);
    }

    @Test
    void readAll() {
        when(data.findAll())
                .thenReturn(Collections.emptyList());

        var actual = service.readAll();

        assertEquals(Collections.emptyList(), actual);

        verify(data).findAll();
        verifyNoMoreInteractions(data);
    }

}
