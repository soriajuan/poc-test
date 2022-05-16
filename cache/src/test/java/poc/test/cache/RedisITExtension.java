package poc.test.cache;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class RedisITExtension implements BeforeAllCallback, AfterAllCallback {

    static final GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", redis::getFirstMappedPort);
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        redis.start();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        redis.stop();
    }

}
