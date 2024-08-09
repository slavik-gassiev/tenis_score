package com.slava;


import com.slava.entity.MyEntity;
import com.slava.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MainApp {

    private static final Logger logger = LogManager.getLogger(MainApp.class);

    public static void main(String[] args) {
        logger.info("Application started");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Создание нового объекта
            MyEntity entity = new MyEntity();
            entity.setName("Test Name");

            // Сохранение объекта в базе данных
            session.save(entity);

            transaction.commit();
            logger.debug("This is a debug message");
        } catch (Exception e) {
            logger.error("An error occurred", e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            logger.info("Application finished");
        }

        HibernateUtil.shutdown();
    }
}

