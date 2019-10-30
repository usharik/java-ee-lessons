package ru.geekbrains;

import ru.geekbrains.model.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EjbClient {

    public static void main(String[] args) throws IOException, NamingException, ExecutionException, InterruptedException {
        Context context = createInitialContext();

        UserService userService = (UserService) context.lookup("ejb:/users/UserServiceImpl!ru.geekbrains.UserService");
        userService.findAll()
                .forEach(u -> System.out.println(u.getUsername()));

        Future<List<User>> future = userService.findAllAsync();
        System.out.println("Waiting for task completion");
        future.get()
                .forEach(u -> System.out.println(u.getUsername()));
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
