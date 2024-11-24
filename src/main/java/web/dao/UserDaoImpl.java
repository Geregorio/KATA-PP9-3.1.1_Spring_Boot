package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import jakarta.persistence.*;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void editUser(User user) {
        em.merge(user);
    }

    @Override
    public void removeUser(Long userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> searchUsersByParams(User searchParams) {
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1");

        if (searchParams.getFirstName() != null && !searchParams.getFirstName().isEmpty()) {
            jpql.append(" AND u.firstName LIKE :firstName");
        }
        if (searchParams.getSecondName() != null && !searchParams.getSecondName().isEmpty()) {
            jpql.append(" AND u.secondName LIKE :secondName");
        }
        if (searchParams.getAge() != 0) {
            jpql.append(" AND u.age = :age");
        }

        TypedQuery<User> query = em.createQuery(jpql.toString(), User.class);

        if (searchParams.getFirstName() != null && !searchParams.getFirstName().isEmpty()) {
            query.setParameter("firstName", "%" + searchParams.getFirstName() + "%");
        }
        if (searchParams.getSecondName() != null && !searchParams.getSecondName().isEmpty()) {
            query.setParameter("secondName", "%" + searchParams.getSecondName() + "%");
        }
        if (searchParams.getAge() != 0) {
            query.setParameter("age", searchParams.getAge());
        }

        return query.getResultList();
    }
}
