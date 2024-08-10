package com.slava.dao;

import com.slava.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class PlayerDAO implements CrudRepository<Player, Long> {

    private EntityManager entityManager;

    public PlayerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Player player) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Player findById(Long id) {
        return entityManager.find(Player.class, id);
    }

    @Override
    public List<Player> findAll() {
        return entityManager.createQuery("SELECT p FROM Player p", Player.class).getResultList();
    }

    @Override
    public void update(Player player) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Player player) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(player) ? player : entityManager.merge(player));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

