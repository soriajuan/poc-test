package poc.test.data.config;

import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.FileSystemResourceAccessor;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseITExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        var masterChangelogFilePath = new File("../migrate/changelogs");
        var resourceAccessor = new FileSystemResourceAccessor(masterChangelogFilePath);

        String url = System.getProperty("spring.datasource.url");
        String username = System.getProperty("spring.datasource.username");
        String password = System.getProperty("spring.datasource.password");
        Connection connection = DriverManager.getConnection(url, username, password);

        var database = new PostgresDatabase();
        database.setConnection(new JdbcConnection(connection));

        try (var liquibase = new Liquibase("db.changelog-master.groovy", resourceAccessor, database)) {
            liquibase.update("integration-testing");
        }
    }

}
