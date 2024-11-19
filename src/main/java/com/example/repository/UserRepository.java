package com.example.repository;

import com.example.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserRepository {
    
    @PersistenceContext
    EntityManager em;
    
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    public User findById(Long id) {
        return em.find(User.class, id);
    }
    
    @Transactional
    public User create(User user) {
        em.persist(user);
        return user;
    }
    
    @Transactional
    public User update(User user) {
        return em.merge(user);
    }
    
    @Transactional
    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            em.remove(user);
        }
    }
} 