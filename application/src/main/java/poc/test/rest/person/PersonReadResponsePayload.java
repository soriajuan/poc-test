package poc.test.rest.person;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
class PersonReadResponsePayload {
    private UUID id;
    private String firstName;
    private String lastName;
}
