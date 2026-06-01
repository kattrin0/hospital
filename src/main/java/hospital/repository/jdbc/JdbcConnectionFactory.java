package hospital.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcConnectionFactory {
    private final String url;
    private final String user;
    private final String password;
    private boolean schemaInitialized;

    public JdbcConnectionFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        if (!schemaInitialized) {
            Migration.migrate(connection);
            schemaInitialized = true;
        }
        return connection;
    }
}