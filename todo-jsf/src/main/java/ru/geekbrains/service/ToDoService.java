package ru.geekbrains.service;

import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@PermitAll
@WebService(endpointInterface = "ru.geekbrains.service.ToDoServiceWs", serviceName = "ToDoService")
public class ToDoService {

    @EJB
    private ToDoRepository toDoRepository;

    public List<ToDoRepr> findAll() {
        return toDoRepository.findAll()
                .stream()
                .map(t -> new ToDoRepr(t.getId(), t.getDescription(), Date.from(t.getTargetDate()
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant())))
                .collect(Collectors.toList());
    }

    public void insert(ToDoRepr todo) {
        ToDo toDo = new ToDo();
        toDo.setDescription(todo.getDescription());
        toDo.setTargetDate(todo.getTargetDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        toDoRepository.insert(toDo);
    }
}
