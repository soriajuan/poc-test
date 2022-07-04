package poc.test.domain.entities;

import lombok.NonNull;

import java.util.UUID;

public record Person(@NonNull UUID id, @NonNull String firstName, @NonNull String lastName) {
}
