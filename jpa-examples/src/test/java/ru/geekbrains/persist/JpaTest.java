package ru.geekbrains.persist;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class JpaTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(User.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
    }

    @Inject
    public UserRepository userRepository;

    @Test
    public void testUserRepository() {
        User user = userRepository.merge(new User(-1, "aaa", "bbb"));
        System.out.println(user);
        System.out.println(user.getRoles());

        List<User> allUsers = userRepository.getAllUsers();
        allUsers.forEach(System.out::println);
    }
}
