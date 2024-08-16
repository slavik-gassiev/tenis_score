package com.slava;

import com.slava.entity.Match;
import com.slava.entity.Player;
import com.slava.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

public class HibernateRunnerTest {

    @Test
    void checkFindByName() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String name = "slava";
        try {
            transaction = session.beginTransaction();

            Player player1 = Player.builder()
                    .name("slava")
                    .build();

            session.persist(player1);

            Player player = session.createQuery("select p FROM Player p where p.name = :playerName", Player.class)
                    .setParameter("playerName", name)
                    .uniqueResult();
            System.out.println(player.getName());
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @Test
    void checkAddMatch() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Player player1 = Player.builder()
                    .name("name1")
                    .build();

            session.persist(player1);

            Player player2 = Player.builder()
                    .name("name2")
                    .build();

            session.persist(player2);

            Match match = Match.builder()
                    .player1(player1)
                    .player2(player2)
                    .winner(player1)
                    .build();

            session.persist(match);

            transaction.commit();
        } catch (Exception e) {

        }
    }
}
