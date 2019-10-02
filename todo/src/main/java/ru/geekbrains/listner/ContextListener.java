package ru.geekbrains.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application");

        ServletContext sc = sce.getServletContext();
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");

        try {
            Connection conn = DriverManager.getConnection(jdbcConnectionString, username, password);
            sc.setAttribute("dbConnection", conn);

            ToDoRepository toDoRepository = new ToDoRepository(conn);
            sc.setAttribute("toDoRepo", toDoRepository);

            if (toDoRepository.findAll().isEmpty()) {
                toDoRepository.insert(new ToDo(-1L, "First", LocalDate.now()));
                toDoRepository.insert(new ToDo(-1L, "Second", LocalDate.now().plusDays(1)));
                toDoRepository.insert(new ToDo(-1L, "Third", LocalDate.now().plusDays(2)));
            }
        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }
}
