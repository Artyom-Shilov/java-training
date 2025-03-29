package com.shilov.repository.impl;

import com.shilov.common.connectivity.DatabaseConnectionManager;
import com.shilov.common.exceptions.RepositoryException;
import com.shilov.models.Space;
import com.shilov.repository.SpaceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class JpaSpaceRepository implements SpaceRepository {

    @Override
    public List<Space> getAllSpaces() {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
           return em.createQuery("SELECT s FROM Space s", Space.class).getResultList();
        }
    }

    @Override
    public Optional<Space> getSpaceById(Long id) {
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            Space space = em.find(Space.class, id);
            return space != null ? Optional.of(space) : Optional.empty();
        }
    }

    @Override
    public Long addSpace(Space space) throws RepositoryException {
        EntityTransaction transaction = null;
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(space);
            transaction.commit();
            return space.getId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteSpace(Long id) throws RepositoryException {
        try (EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Space space = em.find(Space.class, id);
            if (space != null) {
                em.remove(space);
                transaction.commit();
            } else {
                throw new RepositoryException("Space not found");
            }
        }
    }

    @Override
    public void updateSpace(Long id, Space newData) throws RepositoryException {
        EntityTransaction transaction = null;
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            transaction = em.getTransaction();
            transaction.begin();
            Space space = em.find(Space.class, id);
            if (space != null) {
                newData.setId(id);
                em.merge(newData);
                transaction.commit();
            } else {
                transaction.rollback();
                throw new RepositoryException("Space not found");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteAllSpaces() throws RepositoryException {
        EntityTransaction transaction = null;
        try(EntityManager em = DatabaseConnectionManager.getEntityManager()) {
            transaction = em.getTransaction();
            transaction.begin();
            em.createQuery("DELETE FROM Space").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }
}
