package ru.geekbrains;

import ru.geekbrains.model.User;

import javax.ejb.Remote;
import java.util.List;
import java.util.concurrent.Future;

@Remote
public interface UserService {

    List<User> findAll();

    Future<List<User>> findAllAsync();
}
