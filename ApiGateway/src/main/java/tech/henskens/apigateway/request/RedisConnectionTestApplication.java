package tech.henskens.apigateway.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class RedisConnectionTestApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(RedisConnectionTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String key = "testKey";
        String value = "testValue";

        // Set a value in Redis
        template.opsForValue().set(key, value);

        // Get the value from Redis
        String retrievedValue = template.opsForValue().get(key);

        // Check if the retrieved value matches the set value
        if (value.equals(retrievedValue)) {
            System.out.println("Connection to Redis is working");
        } else {
            System.out.println("Connection to Redis is not working");
        }
    }
}
