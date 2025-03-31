package com.shilov.repository.impl;

import com.shilov.common.connectivity.DatabaseConnectionManager;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.User;
import com.shilov.repository.GlobalUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public class JpaGlobalUserRepository implements GlobalUserRepository {

    @Override
    public Long saveUser(User user) throws RepositoryException {
        EntityTransaction transaction = null;
        try (EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            transaction.commit();
            return user.getId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            User user = em.find(User.class, id);
            return user != null ? Optional.of(user) : Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try (EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            return Optional.of(
                    em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                            .setParameter("login", login).getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
