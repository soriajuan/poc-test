package poc.test.rest.person;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import poc.test.domain.entities.Person;
import poc.test.domain.entities.PersonToCreate;
import poc.test.domain.exceptions.EntityNotFoundException;
import poc.test.domain.services.PersonCrudUseCase;

import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonCrudController.class)
class PersonCrudControllerTest {

    static final String BASE_URI = "/persons";

    static final Person johnDoePerson = new Person(UUID.fromString("1b80ae86-1522-48ec-ae26-7197f07fb869"), "John", "Doe");

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonCrudRestMapper mapper;

    @MockBean
    PersonCrudUseCase personCrudUseCase;

    @Test
    void create() throws Exception {
        when(mapper.toPersonToCreate(any(PersonCreateRequestPayload.class)))
                .thenAnswer(invocation -> {
                    var arg = invocation.getArgument(0, PersonCreateRequestPayload.class);
                    return new PersonToCreate(arg.getFirstName(), arg.getLastName());
                });

        var uuid = UUID.randomUUID();
        when(personCrudUseCase.create(any(PersonToCreate.class)))
                .thenReturn(new Person(uuid, "John", "Doe"));

        var resourceFilePath = new ClassPathResource("person-crud-controller-test/createRequestPayload.json", this.getClass().getClassLoader())
                .getFile().toPath();
        var requestPayload = Files.readString(resourceFilePath);

        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/persons/" + uuid))
                .andReturn();

        verify(mapper).toPersonToCreate(any(PersonCreateRequestPayload.class));
        verify(personCrudUseCase).create(any(PersonToCreate.class));
        verifyNoMoreInteractions(mapper, personCrudUseCase);
    }

    @Test
    void readAll() throws Exception {
        when(personCrudUseCase.readAll())
                .thenReturn(List.of(johnDoePerson));

        when(mapper.toPersonReadResponsePayload(any(Person.class)))
                .thenAnswer(invocation -> {
                    var arg = invocation.getArgument(0, Person.class);
                    var res = new PersonReadResponsePayload();
                    res.setId(arg.id());
                    res.setFirstName(arg.firstName());
                    res.setLastName(arg.lastName());
                    return res;
                });

        var responseBody = mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Approvals.verify(responseBody);

        verify(personCrudUseCase).readAll();
        verify(mapper).toPersonReadResponsePayload(any(Person.class));
        verifyNoMoreInteractions(personCrudUseCase, mapper);
    }

    @Test
    void readById_notFound() throws Exception {
        when(personCrudUseCase.readById(any(UUID.class)))
                .thenThrow(new EntityNotFoundException("entity person not found under id " + returnsFirstArg()));

        mockMvc.perform(get(BASE_URI + "/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(personCrudUseCase).readById(any(UUID.class));
        verifyNoInteractions(mapper);
    }

    @Test
    void readById() throws Exception {
        when(personCrudUseCase.readById(any(UUID.class)))
                .thenReturn(johnDoePerson);

        when(mapper.toPersonReadResponsePayload(any(Person.class)))
                .thenAnswer(invocation -> {
                    var arg = invocation.getArgument(0, Person.class);
                    var res = new PersonReadResponsePayload();
                    res.setId(arg.id());
                    res.setFirstName(arg.firstName());
                    res.setLastName(arg.lastName());
                    return res;
                });

        var responseBody = mockMvc.perform(get(BASE_URI + "/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Approvals.verify(responseBody);

        verify(personCrudUseCase).readById(any(UUID.class));
        verify(mapper).toPersonReadResponsePayload(any(Person.class));
    }

}
