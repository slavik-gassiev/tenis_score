package com.slava.servlets;


import com.slava.dao.MatchDAO;
import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import com.slava.entity.Match;
import com.slava.entity.Player;
import com.slava.service.GameService;
import com.slava.service.OnGoingMatchService;
import com.slava.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;

@WebServlet(name = "ScoreServlet", value = "/match-score")
public class ScoreServlet extends HttpServlet {

    private OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();
    private GameService gameService = new GameService();
    private Session session = HibernateUtil.getSessionFactory().openSession();
    private MatchDAO matchDAO = new MatchDAO(session);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid;
        uuid = request.getParameter("uuid");
        if (!isUIIDCorrect(request, response, uuid)) return;

        MatchDTO matchDTO = onGoingMatchService.getMatch(uuid);
        setAttributes(request, uuid, matchDTO);

        if (matchDTO.isFinished()) {
            insetMatchToDataBase(matchDTO);
            request.getRequestDispatcher("finished-match.jsp").forward(request, response);
        }

        request.getRequestDispatcher("match-score.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        PlayerDTO playerDTO = getPointWinner(req, uuid);

        gameService.playerPoint(uuid, playerDTO);

        String redirect = String.format("/tenis_score_war_exploded/match-score?uuid=%s", uuid);
        resp.sendRedirect(redirect);


    }

    private void setAttributes(HttpServletRequest request, String uuid, MatchDTO matchDTO) {
        request.setAttribute("uuid", uuid);
        request.setAttribute("p1name", matchDTO.getPlayer1());
        request.setAttribute("p2name",  matchDTO.getPlayer2());
        request.setAttribute("p1score", matchDTO.getPlayer1CurrentScore());
        request.setAttribute("p2score", matchDTO.getPlayer2CurrentScore());
        request.setAttribute("p1deuce", matchDTO.getPlayer1CurrentDeuce());
        request.setAttribute("p2deuce", matchDTO.getPlayer2CurrentDeuce());
        request.setAttribute("p1game", matchDTO.getPlayer1GamesWon());
        request.setAttribute("p2game", matchDTO.getPlayer2GamesWon());
        request.setAttribute("p1TieBreak", matchDTO.getPlayer1TieBreaksWon());
        request.setAttribute("p2TieBreak", matchDTO.getPlayer2TieBreaksWon());
        request.setAttribute("p1set", matchDTO.getPlayer1SetWon());
        request.setAttribute("p2set", matchDTO.getPlayer2SetWon());
        request.setAttribute("winner", matchDTO.getWinner());
    }

    private void insetMatchToDataBase(MatchDTO matchDTO) {
        Player player1 = Player.builder()
                .name(matchDTO.getPlayer1().getName())
                .build();

        Player player2 = Player.builder()
                .name(matchDTO.getPlayer1().getName())
                .build();

        Player winner = Player.builder()
                .name(matchDTO.getWinner().getName())
                .build();

        Match toInsert = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(winner)
                .build();

        matchDAO.save(toInsert);
        session.close();
    }

    private PlayerDTO getPointWinner(HttpServletRequest req, String uuid) {
        PlayerDTO playerDTO = null;

        if (req.getParameter("point_winner").equals("p1")) {
             playerDTO = onGoingMatchService.getMatch(uuid).getPlayer1();
        }
        if (req.getParameter("point_winner").equals("p2")) {
             playerDTO = onGoingMatchService.getMatch(uuid).getPlayer2();
        }
        return playerDTO;
    }

    private boolean isUIIDCorrect(HttpServletRequest request, HttpServletResponse response, String uuid) throws IOException {
        if(request.getParameterMap().isEmpty()) {
            response.sendError(400, "No parameters!");
            return false;
        }
        if (uuid.isBlank()) {
            response.sendError(400, "Invalid uuid!");
            return false;
        }
        if (!onGoingMatchService.isMatchExist(uuid)) {
            response.sendError(400, "No match with this uuid");
            return false;
        }
        return true;
    }
}