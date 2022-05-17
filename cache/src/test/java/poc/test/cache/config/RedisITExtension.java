package poc.test.cache.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RedisITExtension implements BeforeAllCallback {

    public static final GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", String.valueOf(redis.getFirstMappedPort()));
    }

}
