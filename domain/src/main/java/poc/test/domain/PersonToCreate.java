package poc.test.domain;

import lombok.NonNull;

public record PersonToCreate(@NonNull String firstName, @NonNull String lastName) {
}
