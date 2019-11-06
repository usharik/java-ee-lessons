package ru.geekbrains;

import ru.geekbrains.service.ToDoRepr;
import ru.geekbrains.service.ToDoService;
import ru.geekbrains.service.ToDoServiceWs;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

public class WsClient {

    public static void main(String[] args) throws MalformedURLException, DatatypeConfigurationException {
        URL url = new URL("http://localhost:8080/todo-jsf/ToDoService/ToDoService?WSDL");
        ToDoService toDoService = new ToDoService(url);

        ToDoServiceWs toDoServicePort = toDoService.getToDoServicePort();

        ToDoRepr toDoRepr = new ToDoRepr();
        toDoRepr.setDescription("From SOAP service 1");

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        toDoRepr.setTargetDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

        toDoServicePort.insert(toDoRepr);

        toDoServicePort.findAll()
                .forEach(t -> System.out.println(t.getDescription()));
    }
}
