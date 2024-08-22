package com.slava;

import com.slava.dao.MatchDAO;
import com.slava.dao.PlayerDAO;
import com.slava.dto.MatchDTO;
import com.slava.entity.Match;
import com.slava.entity.Player;
import com.slava.util.HibernateUtil;
import com.slava.util.MapperUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperTestUtil {
    @Test
    void checkMedelMapper() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MatchDAO matchDAO = new MatchDAO(session);
        PlayerDAO playerDAO = new PlayerDAO(session);
        ModelMapper mapper = MapperUtil.getModelMapper();

        Player player1 = Player.builder().name("John Doe").build();
        Player player2 = Player.builder().name("John Do0000e").build();
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

        List<Match> matches =  matchDAO.findAll();
        List<MatchDTO> matchesDTO = matches.stream()
                .map(match -> mapper.map(match, MatchDTO.class))
                .collect(Collectors.toList());

        for (MatchDTO match : matchesDTO) {
            String name1 = match.getPlayer1().getName();
            String name2 = match.getPlayer2().getName();
            String winner = match.getWinner().getName();
            System.out.println(name1);
            System.out.println(name2);
            System.out.println(winner);
        }
    }
}
