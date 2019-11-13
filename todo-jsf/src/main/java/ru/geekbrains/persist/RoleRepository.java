package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RoleRepository {

    private Logger logger = LoggerFactory.getLogger(RoleRepository.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @PostConstruct
    public void init() {
        logger.info("Role table is empty. Initializing.");

        Long roleCount = em.createQuery("select count(*) from Role", Long.class).getSingleResult();
        if (roleCount == 0L) {
            em.merge(new Role("ADMIN"));
            em.merge(new Role("GUEST"));
        }
    }

    @TransactionAttribute
    public Role merge(Role role) {
        return em.merge(role);
    }

    @TransactionAttribute
    public Role findById(int id) {
        return em.find(Role.class, id);
    }

    @TransactionAttribute
    public List<Role> getAllRoles() {
        return em.createQuery("from Role ", Role.class).getResultList();
    }
}
