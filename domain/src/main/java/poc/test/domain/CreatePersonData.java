package poc.test.domain;

import lombok.NonNull;

public interface CreatePersonData {
    Person insert(@NonNull PersonToCreate p);
}
