package com.slava.servlets;

import com.slava.dao.MatchDAO;
import com.slava.dto.MatchDTO;
import com.slava.entity.Match;
import com.slava.exceptions.MatchesNotFoundException;
import com.slava.util.HibernateUtil;
import com.slava.util.MapperUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "AllMatchesServlet", value = "/all")
public class AllMatchesServlet extends HttpServlet {
    private Session session = HibernateUtil.getSessionFactory().openSession();
    MatchDAO matchDAO = new MatchDAO(session);

    private ModelMapper mapper = MapperUtil.getModelMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MatchDTO> matchesDTO = null;
        try {
            List<Match> matches =  matchDAO.findAll();
//            session.close();
             matchesDTO = matches.stream()
                    .map(match -> mapper.map(match, MatchDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MatchesNotFoundException("Не удалось загрузить матчи");
        }
        if (matchesDTO != null) {
            req.setAttribute("matches", matchesDTO);
        }
        req.getRequestDispatcher("/all-matches.jsp").forward(req, resp);
    }


}
