package com.slava;

import com.slava.entity.Matche;
import com.slava.entity.Player;
import com.slava.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

public class HibernateRunnerTest {

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

            Matche matche = Matche.builder()
                    .player1(player1)
                    .player2(player2)
                    .winner(player1)
                    .build();

            session.persist(matche);

            transaction.commit();
        } catch (Exception e) {

        }
    }
}
