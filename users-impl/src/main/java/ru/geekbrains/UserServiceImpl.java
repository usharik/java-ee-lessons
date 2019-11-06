package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.model.User;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Stateless
public class UserServiceImpl implements UserService, UserServiceRest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final List<User> users = Arrays.asList(
            new User(1L, "user1", "ppp"),
            new User(2L, "user2", "ppp"),
            new User(3L, "user3", "ppppp"),
            new User(4L, "user4", "ppppp"),
            new User(5L, "user5", "ppppp")
    );

    @Override
    public List<User> findAll() {
        logger.info("findAll() invocation");
        return users;
    }

    @Override
    public User getUser(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
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
