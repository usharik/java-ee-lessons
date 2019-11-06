package ru.geekbrains.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ToDoServiceWs {

    @WebMethod
    List<ToDoRepr> findAll();

    @WebMethod
    void insert(ToDoRepr todo);
}
