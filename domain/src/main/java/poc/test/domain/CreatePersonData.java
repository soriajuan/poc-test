package poc.test.domain;

import org.springframework.lang.NonNull;

public interface CreatePersonData {
    Person insert(@NonNull Person p);
}
