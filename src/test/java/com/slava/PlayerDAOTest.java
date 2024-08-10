package com.slava;

import com.slava.dao.PlayerDAO;
import com.slava.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private PlayerDAO playerDAO;

    @BeforeAll
    public void setUp() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    @BeforeEach
    public void init() {
        var session = sessionFactory.openSession();
        playerDAO = new PlayerDAO(session);
    }

    @AfterEach
    public void tearDown() {
        if (playerDAO.getSession() != null) {
            playerDAO.getSession().close();
        }
    }

    @AfterAll
    public void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testSavePlayer() {
        Player player = Player.builder().name("John Doe").build();
        playerDAO.save(player);

        Player foundPlayer = playerDAO.findById(player.getId());
        assertNotNull(foundPlayer);
        assertEquals("John Doe", foundPlayer.getName());
    }

    @Test
    public void testFindById() {
        Player player = Player.builder().name("Jane Doe").build();
        playerDAO.save(player);

        Player foundPlayer = playerDAO.findById(player.getId());
        assertNotNull(foundPlayer);
        assertEquals("Jane Doe", foundPlayer.getName());
    }

    @Test
    public void testFindAll() {
        Player player1 = Player.builder().name("Player One").build();
        Player player2 = Player.builder().name("Player Two").build();
        playerDAO.save(player1);
        playerDAO.save(player2);

        List<Player> players = playerDAO.findAll();
        assertEquals(2, players.size());
    }

    @Test
    public void testUpdatePlayer() {
        Player player = Player.builder().name("Jane Doe").build();
        playerDAO.save(player);

        player.setName("John Smith");
        playerDAO.update(player);

        Player updatedPlayer = playerDAO.findById(player.getId());
        assertEquals("John Smith", updatedPlayer.getName());
    }

    @Test
    public void testDeletePlayer() {
        Player player = Player.builder().name("Jane Doe").build();
        playerDAO.save(player);

        playerDAO.delete(player);

        Player deletedPlayer = playerDAO.findById(player.getId());
        assertNull(deletedPlayer);
    }
}

