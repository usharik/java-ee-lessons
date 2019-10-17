package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
@Named
public class DataSource {

    private static final Logger logger = LoggerFactory.getLogger(DataSource.class);

    @Inject
    private ServletContext sc;

    private Connection connection;

    @PostConstruct
    public void init() throws SQLException {
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");

        this.connection = DriverManager.getConnection(jdbcConnectionString, username, password);
    }

    @PreDestroy
    public void close() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
