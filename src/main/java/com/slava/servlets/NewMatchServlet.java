package com.slava.servlets;

import com.slava.dao.CrudRepository;
import com.slava.dao.MatchDAO;
import com.slava.dao.PlayerDAO;
import com.slava.dao.PlayerDAOInterface;
import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import com.slava.entity.Match;
import com.slava.entity.Player;
import com.slava.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;

@WebServlet("/new")
public class NewMatchServlet extends HttpServlet {

    private Session session = HibernateUtil.getSessionFactory().openSession();
    PlayerDAOInterface playerDao = new PlayerDAO(session);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String p1name = req.getParameter("p1name");
        String p2name = req.getParameter("p2name");

        initPlayersAndMatch(p1name, p2name);

    }

    private void initPlayersAndMatch(String p1name, String p2name) {
        PlayerDTO player1DTO = null;
        PlayerDTO player2DTO = null;

        if (playerDao.findByName(p1name).equals(null)) {
            playerDao.save(Player.builder().name(p1name).build());
            player1DTO = PlayerDTO.builder().name(p1name).build();
        }
        if (playerDao.findByName(p2name).equals(null)) {
            playerDao.save(Player.builder().name(p2name).build());
            player2DTO = PlayerDTO.builder().name(p2name).build();
        }

        MatchDTO matchDTO = MatchDTO.builder()
                .player1(player1DTO)
                .player2(player2DTO)
                .isFinished(false)
                .isDeuce(false)
                .isTieBreak(false)
                .build();
    }
}
