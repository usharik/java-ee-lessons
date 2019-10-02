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

@WebServlet(name = "ToDoServlet", urlPatterns = "/todos/*")
public class ToDoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ToDoServlet.class);

    private ToDoRepository repository;

    @Override
    public void init() throws ServletException {
        this.repository = (ToDoRepository) getServletContext().getAttribute("toDoRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("PathInfo: {}", req.getPathInfo());

        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            try {
                req.setAttribute("todos", repository.findAll());
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            req.getRequestDispatcher("/WEB-INF/templates/index.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/new")) {
            req.setAttribute("todo", new ToDo());
            req.setAttribute("action", "create");
            req.getRequestDispatcher("/WEB-INF/templates/todo.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/edit")) {
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
            req.getRequestDispatcher("/WEB-INF/templates/todo.jsp").forward(req, resp);
        } else if (req.getPathInfo().equals("/delete")) {
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
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Date {}", req.getParameter("targetDate"));

        if (req.getPathInfo() != null && req.getPathInfo().equals("/update")) {
            try {
                repository.update(new ToDo(
                        Long.parseLong(req.getParameter("id")),
                        req.getParameter("description"),
                        LocalDate.parse(req.getParameter("targetDate"))));
                resp.sendRedirect(getServletContext().getContextPath() + "/todos");
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException|DateTimeParseException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (req.getPathInfo() != null && req.getPathInfo().equals("/create")) {
            try {
                repository.insert(new ToDo(
                        -1L,
                        req.getParameter("description"),
                        LocalDate.parse(req.getParameter("targetDate"))));
                resp.sendRedirect(getServletContext().getContextPath() + "/todos");
            } catch (SQLException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NumberFormatException|DateTimeParseException ex) {
                logger.error("", ex);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

