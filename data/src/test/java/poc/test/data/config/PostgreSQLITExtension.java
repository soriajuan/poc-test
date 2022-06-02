package poc.test.data.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

public class PostgreSQLITExtension implements BeforeAllCallback {

    static final String SERVICE_NAME = "postgres";
    static final Integer SERVICE_PORT = 5432;

    static final DockerComposeContainer<?> environment =
            new DockerComposeContainer<>(new File("src/test/resources/docker/docker-compose.yml"))
                    .withExposedService(SERVICE_NAME, SERVICE_PORT);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        environment.start();
        var jdbcUrl = String.format("jdbc:postgresql://%s:%d/my_db",
                environment.getServiceHost(SERVICE_NAME, SERVICE_PORT),
                environment.getServicePort(SERVICE_NAME, SERVICE_PORT));
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "my_user");
        System.setProperty("spring.datasource.password", "my_user");
    }

}
