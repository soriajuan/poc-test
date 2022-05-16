package poc.test.cache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(RedisITExtension.class)
class RedisPutTest {

    static final String KEY_VALUE_MAP = "KeyValueMap";
    static RedissonClient redisson;

    @BeforeAll
    static void beforeAll() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + RedisITExtension.redis.getHost() + ":" + RedisITExtension.redis.getFirstMappedPort());
        redisson = Redisson.create(config);
    }

    @Test
    void put() {
        var key = "someKey";
        var value = "someValue";

        redisson.getMap(KEY_VALUE_MAP).put(key, value);

        String actual = (String) redisson.getMap(KEY_VALUE_MAP).get(key);

        assertEquals(value, actual);
    }

}
