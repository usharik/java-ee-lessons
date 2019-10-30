package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.model.User;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Stateless
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final List<User> users = Arrays.asList(
            new User("user1", "ppp"),
            new User("user2", "ppp"),
            new User("user3", "ppppp"),
            new User("user4", "ppppp"),
            new User("user5", "ppppp")
    );

    @Override
    public List<User> findAll() {
        logger.info("findAll() invocation");
        return users;
    }

    @Override
    @Asynchronous
    public Future<List<User>> findAllAsync() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(users);
    }
}
