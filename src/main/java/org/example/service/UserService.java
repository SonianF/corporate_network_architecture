package org.example.service;

import org.example.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class UserService{

    @PersistenceContext(unitName = "MusicStreamingPU")
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery(
                "SELECT u FROM User u ORDER BY u.id",
                User.class).getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public void create(User user) {
        em.persist(user);
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(Long id) {
        User user = em.find(User.class, id);

        if (user != null) {
            em.remove(user);
        }
    }

}
