package ru.itsinfo.dao;

import org.springframework.stereotype.Repository;
import ru.itsinfo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> readAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public User createUser(User user) {
//        entityManager.persist(user);
        return entityManager.merge(user);
    }

    @Override
    @Transactional
    public User deleteUser(Integer userId) {
        User user = readUser(userId);
        entityManager.remove(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User readUser(Integer userId) {
        return entityManager.find(User.class, userId);
    }
}
