package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static ru.geekbrains.listner.ContextListener.TODO_REPO;

@WebServlet(name = "ToDoServlet", urlPatterns = {"", "/"})
public class ToDoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ToDoServlet.class);

    private ToDoRepository repository;

    @Override
    public void init() throws ServletException {
        this.repository = (ToDoRepository) getServletContext().getAttribute(TODO_REPO);

        if (this.repository == null) {
            throw new ServletException("TodoRepository is not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("PathInfo: {}", req.getPathInfo());
        logger.info("ServletPath: {}", req.getServletPath());
        logger.info("ResourceURL: {}", getServletContext().getResource("/WEB-INF/templates/index.jsp"));

        if (req.getServletPath().equals("/")) {
            showAllTodos(req, resp);
        } else if (req.getServletPath().equals("/new")) {
            showNewTodoPage(req, resp);
        } else if (req.getServletPath().equals("/edit")) {
            showEditTodoPage(req, resp);
        } else if (req.getServletPath().equals("/delete")) {
            deleteTodo(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Date {}", req.getParameter("targetDate"));

        if (req.getServletPath().equals("/update")) {
            updateTodo(req, resp);
        } else if (req.getServletPath().equals("/create")) {
            createTodo(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showAllTodos(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            req.setAttribute("todos", repository.findAll());
        } catch (SQLException ex) {
            logger.error("", ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/templates/index.jsp").forward(req, resp);
    }

    private void showNewTodoPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("todo", new ToDo());
        req.setAttribute("action", "create");
        getServletContext().getRequestDispatcher("/WEB-INF/templates/todo.jsp").forward(req, resp);
    }

    private void showEditTodoPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (Exception ex) {
            logger.error("", ex);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        ToDo toDo;
        try {
            toDo = repository.findById(id);
        } catch (SQLException ex) {
            logger.error("", ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        req.setAttribute("todo", toDo);
        req.setAttribute("action", "update");
        getServletContext().getRequestDispatcher("/WEB-INF/templates/todo.jsp").forward(req, resp);
    }

    private void deleteTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (Exception ex) {
            logger.error("", ex);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            return;
        }
        try {
            repository.delete(id);
            resp.sendRedirect(getServletContext().getContextPath() + "/todos");
        } catch (SQLException ex) {
            logger.error("", ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void updateTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            repository.update(new ToDo(
                    Long.parseLong(req.getParameter("id")),
                    req.getParameter("description"),
                    LocalDate.parse(req.getParameter("targetDate"))));
            resp.sendRedirect(getServletContext().getContextPath());
        } catch (SQLException ex) {
            logger.error("", ex);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException|DateTimeParseException ex) {
            logger.error("", ex);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void createTodo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            repository.insert(new ToDo(
                    -1L,
                    req.getParameter("description"),
                    LocalDate.parse(req.getParameter("targetDate"))));
            resp.sendRedirect(getServletContext().getContextPath());
        } catch (SQLException ex) {
            logger.error("", ex);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException|DateTimeParseException ex) {
            logger.error("", ex);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

