package com.slava.servlets;

import com.slava.dao.PlayerDAO;
import com.slava.dao.PlayerDAOInterface;
import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import com.slava.entity.Player;
import com.slava.service.OnGoingMatchService;
import com.slava.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "NewMatchServlet", value = "/new")
public class NewMatchServlet extends HttpServlet {

    private Session session = HibernateUtil.getSessionFactory().openSession();
    private OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();
    PlayerDAOInterface playerDao = new PlayerDAO(session);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String p1name = req.getParameter("p1name");
        String p2name = req.getParameter("p2name");

        if (!isPlayersNamesCorrect(resp, p1name, p2name)) return;

        MatchDTO matchDTO = initPlayersAndMatch(p1name, p2name);
        UUID uuid =  onGoingMatchService.saveMatch(matchDTO);
        String redirect = String.format("/tenis_score_war_exploded/match-score?uuid=%s", uuid);
        resp.sendRedirect(redirect);


    }

    private MatchDTO initPlayersAndMatch(String p1name, String p2name) {
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

        return MatchDTO.builder()
                .player1(player1DTO)
                .player2(player2DTO)
                .isFinished(false)
                .isDeuce(false)
                .isTieBreak(false)
                .build();
    }

    private boolean isPlayersNamesCorrect(HttpServletResponse resp, String p1name, String p2name) throws IOException {
        if (p1name == null || p2name == null) {
            resp.sendError(400, "Invalid player name!");
            return false;
        }
        if (p1name.isBlank() || p2name.isBlank()) {
            resp.sendError(400, "invalid player name!");
            return false;
        }
        if (p1name.equals(p2name)) {
            resp.sendError(400, "Players names can't be same!");
            return false;
        }
        return true;
    }
}
