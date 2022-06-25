package poc.test.data.config;

import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SingletonPostgresContainer {

    private static final String SERVICE_NAME = "postgres";
    private static final Integer SERVICE_PORT = 5432;

    private static final String JDBC_URL;
    private static final String USERNAME = "my_user";
    private static final String PASSWORD = "my_user";

    private static final DockerComposeContainer<?> dockerComposeContainer =
            new DockerComposeContainer<>(new File("src/test/resources/docker/docker-compose.yml"))
                    .withExposedService(SERVICE_NAME, SERVICE_PORT);

    static {
        var f = new File("docker/postgres-test-compose.yml");
        System.out.println("juan exists: " + f.exists());
        System.out.println("juan path: " + f.getAbsolutePath());
        dockerComposeContainer.start();

        JDBC_URL = String.format("jdbc:postgresql://%s:%d/my_db",
                dockerComposeContainer.getServiceHost(SERVICE_NAME, SERVICE_PORT),
                dockerComposeContainer.getServicePort(SERVICE_NAME, SERVICE_PORT));

        updateSpringProperties();
        runLiquibaseMigrations();
    }

    private static void updateSpringProperties() {
        System.setProperty("spring.datasource.url", JDBC_URL);
        System.setProperty("spring.datasource.username", USERNAME);
        System.setProperty("spring.datasource.password", PASSWORD);
    }

    private static void runLiquibaseMigrations() {
        var masterChangelogFilePath = new File("src/main/resources/db/changelog");
        var resourceAccessor = new FileSystemResourceAccessor(masterChangelogFilePath);

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("error getting database connection", e);
        }

        var database = new PostgresDatabase();
        database.setConnection(new JdbcConnection(connection));

        try (var liquibase = new Liquibase("db.changelog-master.groovy", resourceAccessor, database)) {
            liquibase.update("integration-testing");
        } catch (LiquibaseException e) {
            throw new RuntimeException("error executing liquibase migrations", e);
        }

    }

}
