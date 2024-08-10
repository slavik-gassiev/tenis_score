package com.slava.dao;

import com.slava.entity.Match;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MatchDAO implements CrudRepository<Match, Long> {

    private Session session;

    public MatchDAO(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void save(Match match) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Match findById(Long id) {
        return session.get(Match.class, id);
    }

    @Override
    public List<Match> findAll() {
        return session.createQuery("FROM Match", Match.class).list();
    }

    @Override
    public void update(Match match) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Match match) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}


