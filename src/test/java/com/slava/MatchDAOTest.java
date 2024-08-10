package com.slava;

import com.slava.dao.MatchDAO;
import com.slava.dao.PlayerDAO;
import com.slava.entity.Match;
import com.slava.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchDAOTest {

    private SessionFactory sessionFactory;
    private Session session;
    private MatchDAO matchDAO;
    private PlayerDAO playerDAO;

    @BeforeAll
    public void setUp() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    @BeforeEach
    public void init() {
        session = sessionFactory.openSession();
        matchDAO = new MatchDAO(session);
        playerDAO = new PlayerDAO(session);
    }

    @AfterEach
    public void tearDown() {

        if (playerDAO.getSession() != null) {
            playerDAO.getSession().close();
        } else  if (matchDAO.getSession() != null) {
            matchDAO.getSession().close();
        }
    }

    @AfterAll
    public void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testSaveMatch() {

        Player player1 = Player.builder().name("John Doe").build();
        Player player2 = Player.builder().name("John Doe").build();
        playerDAO.save(player1);
        playerDAO.save(player2);

        Match match = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(player1)
                .build();
        matchDAO.save(match);


        Match foundMatch = matchDAO.findById(match.getId());
        assertNotNull(foundMatch);
        assertEquals("John Doe", foundMatch.getPlayer1().getName());
    }

    @Test
    public void testFindById() {

        Player player1 = Player.builder().name("John Doe").build();
        Player player2 = Player.builder().name("John Doe").build();
        playerDAO.save(player1);
        playerDAO.save(player2);

        Match match = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(player1)
                .build();

        matchDAO.save(match);


        Match foundMatch = matchDAO.findById(match.getId());
        assertNotNull(foundMatch);
        assertEquals(player1, foundMatch.getPlayer1());
        assertEquals(player2, foundMatch.getPlayer2());
    }

    @Test
    public void testFindAll() {

        Player player1 = Player.builder().name("John Doe").build();
        Player player2 = Player.builder().name("John Doe").build();
        Player player3 = Player.builder().name("Bob Smith").build();

        playerDAO.save(player1);
        playerDAO.save(player2);
        playerDAO.save(player3);

        Match match1 = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(player1)
                .build();

        Match match2 = Match.builder()
                .player1(player2)
                .player2(player3)
                .winner(player3)
                .build();

        matchDAO.save(match1);
        matchDAO.save(match2);


        List<Match> matches = matchDAO.findAll();
        assertEquals(2, matches.size());
    }

    @Test
    public void testUpdateMatch() {

        Player player1 = Player.builder().name("John Doe").build();
        Player player2 = Player.builder().name("John Doe").build();
        playerDAO.save(player1);
        playerDAO.save(player2);

        Match match = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(player1)
                .build();

        matchDAO.save(match);


        match.setWinner(player2);
        matchDAO.update(match);

        Match updatedMatch = matchDAO.findById(match.getId());
        assertEquals(player2, updatedMatch.getWinner());
    }

    @Test
    public void testDeleteMatch() {
        Player player1 = Player.builder().name("John Doe").build();
        Player player2 = Player.builder().name("John Doe").build();
        playerDAO.save(player1);
        playerDAO.save(player2);

        Match match = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(player1)
                .build();

        matchDAO.save(match);

        matchDAO.delete(match);

        Match deletedMatch = matchDAO.findById(match.getId());
        assertNull(deletedMatch);
    }
}
