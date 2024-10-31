package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        em.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(long userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public List<User> searchUsers(User searchCriteria) {
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1");

        if (searchCriteria.getFirstName() != null && !searchCriteria.getFirstName().isEmpty()) {
            jpql.append(" AND u.firstName LIKE :firstName");
        }
        if (searchCriteria.getSecondName() != null && !searchCriteria.getSecondName().isEmpty()) {
            jpql.append(" AND u.secondName LIKE :secondName");
        }
        if (searchCriteria.getAge() != 0) {
            jpql.append(" AND u.age = :age");
        }

        TypedQuery<User> query = em.createQuery(jpql.toString(), User.class);

        if (searchCriteria.getFirstName() != null && !searchCriteria.getFirstName().isEmpty()) {
            query.setParameter("firstName", "%" + searchCriteria.getFirstName() + "%");
        }
        if (searchCriteria.getSecondName() != null && !searchCriteria.getSecondName().isEmpty()) {
            query.setParameter("secondName", "%" + searchCriteria.getSecondName() + "%");
        }
        if (searchCriteria.getAge() != 0) {
            query.setParameter("age", searchCriteria.getAge());
        }

        return query.getResultList();
    }
}
