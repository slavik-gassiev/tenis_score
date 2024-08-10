package com.slava.dao;

import com.slava.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerDAO implements CrudRepository<Player, Long> {

    private Session session;

    public PlayerDAO(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void save(Player player) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Player findById(Long id) {
        return session.get(Player.class, id);
    }

    @Override
    public List<Player> findAll() {
        return session.createQuery("SELECT p FROM Player p", Player.class).getResultList();
    }

    @Override
    public void update(Player player) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Player player) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

