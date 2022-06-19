package poc.test.domain;

import java.util.UUID;

public record Person(UUID id, String firstName, String lastName) {
}
