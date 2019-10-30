package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Stateless
//@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class ToDoRepository implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @PostConstruct
    public void init() {
        if (this.findAll().isEmpty()) {
            this.insert(new ToDo(-1L, "First", LocalDate.now()));
            this.insert(new ToDo(-1L, "Second", LocalDate.now().plusDays(1)));
            this.insert(new ToDo(-1L, "Third", LocalDate.now().plusDays(2)));
        }
    }

    @TransactionAttribute
    public void insert(ToDo toDo) {
        em.persist(toDo);
    }

    @TransactionAttribute
    public void update(ToDo toDo) {
        em.merge(toDo);
    }

    @TransactionAttribute
    public void delete(long id) {
        ToDo toDo = em.find(ToDo.class, id);
        if (toDo != null) {
            em.remove(toDo);
        }
    }

    public ToDo findById(long id) {
        return em.find(ToDo.class, id);
    }

    public List<ToDo> findAll() {
        return em.createQuery("from ToDo", ToDo.class).getResultList();
    }
}
