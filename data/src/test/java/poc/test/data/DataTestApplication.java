package poc.test.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"poc.test.data"})
public class DataTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataTestApplication.class, args);
    }

}
