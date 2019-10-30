package ru.geekbrains;

import ru.geekbrains.model.User;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserService {

    List<User> findAll();
}
