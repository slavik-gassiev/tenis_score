package com.slava.servlets;


import com.slava.service.OnGoingMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ScoreServlet", value = "/match-score")
public class ScoreServlet extends HttpServlet {

    private OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid;
        uuid = request.getParameter("uuid");
        if (!isUIIDCorrect(request, response, uuid)) return;

        request.setAttribute("uuid", uuid);
        request.getRequestDispatcher("match-score.jsp").forward(request, response);
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
        if (onGoingMatchService.getMatch(uuid).isEmpty()) {
            response.sendError(400, "No match with this uuid");
            return false;
        }
        return true;
    }
}