package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class TodoBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TodoBean.class);

    @Inject
    private ToDoRepository toDoRepository;

    private ToDo toDo;

    public ToDo getToDo() {
        return toDo;
    }

    public void setToDo(ToDo toDo) {
        this.toDo = toDo;
    }

    public List<ToDo> getAllTodo() throws SQLException {
        return toDoRepository.findAll();
    }

    public String createTodo() {
        this.toDo = new ToDo();
        return "/todo.xhtml?faces-redirect=true";
    }

    public String saveTodo() throws SQLException {
        if (toDo.getId() == null) {
            toDoRepository.insert(toDo);
        } else {
            toDoRepository.update(toDo);
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public void deleteTodo(ToDo toDo) throws SQLException {
        logger.info("Deleting ToDo.");
        toDoRepository.delete(toDo.getId());
    }

    public String editTodo(ToDo toDo) {
        this.toDo = toDo;
        return "/todo.xhtml?faces-redirect=true";
    }
}
