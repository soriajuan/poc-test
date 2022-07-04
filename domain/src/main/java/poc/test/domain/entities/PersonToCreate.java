package poc.test.domain.entities;

import lombok.NonNull;

public record PersonToCreate(@NonNull String firstName, @NonNull String lastName) {
}
