package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class UsersBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(UsersBean.class);

    @EJB
    private UserRepository userRepository;

    @EJB
    private RoleRepository roleRepository;

    @Inject
    private HttpServletRequest request;

    private User user;

    private List<Role> roles;

    @PostConstruct
    public void init() {
        this.roles = roleRepository.getAllRoles();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public String editUser(User user) {
        this.user = user;
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public void deleteUser(User user) {
        userRepository.delete(user.getId());
    }

    public String createUser() {
        this.user = new User();
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        logger.info("Saving user");

        userRepository.merge(this.user);
        return "/admin/users.xhtml?faces-redirect=true";
    }

    public List<Role> getAllRoles() {
        return roles;
    }

    public String logout() throws ServletException {
        request.logout();
        return "/index.xhtml?faces-redirect=true";
    }
}
