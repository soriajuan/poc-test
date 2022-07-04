package poc.test.rest.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poc.test.domain.services.PersonCrudUseCase;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("persons")
class PersonCrudController {

    private final PersonCrudRestMapper mapper;
    private final PersonCrudUseCase personCrudUseCase;

    @PostMapping
    ResponseEntity<Void> create(@RequestBody @Valid PersonCreateRequestPayload payload) {
        var toCreate = mapper.toPersonToCreate(payload);
        var created = personCrudUseCase.create(toCreate);
        return ResponseEntity.created(URI.create("/persons/" + created.id())).build();
    }

    @GetMapping
    List<PersonReadResponsePayload> readAll() {
        return personCrudUseCase.readAll().stream().map(mapper::toPersonReadResponsePayload).toList();
    }

    @GetMapping("{id}")
    PersonReadResponsePayload readById(@PathVariable(value = "id") UUID id) {
        var person = personCrudUseCase.readById(id);
        return mapper.toPersonReadResponsePayload(person);
    }

}
