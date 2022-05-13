package poc.test.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Person {

    private UUID id;
    private String firstName;
    private String lastName;

}
