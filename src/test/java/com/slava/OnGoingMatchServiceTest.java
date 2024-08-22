package com.slava;

import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import com.slava.service.OnGoingMatchService;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.util.UUID;

public class OnGoingMatchServiceTest {

    @Test
    void checkGetMatch() {
        OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();
        PlayerDTO player1 = PlayerDTO.builder()
                .name("Slava1")
                .build();

        PlayerDTO player2 = PlayerDTO.builder()
                .name("Slava2")
                .build();

        MatchDTO matchDTO = MatchDTO.builder()
                .player1(player1)
                .player2(player2)
                .isTieBreak(false)
                .isDeuce(false)
                .isFinished(false)
                .build();

        String playerName = matchDTO.getPlayer1().getName();
        System.out.println(playerName);

        String uuid = onGoingMatchService.saveMatch(matchDTO);
        MatchDTO matchDTO1 = onGoingMatchService.getMatch(uuid);
        System.out.println(matchDTO1.isFinished());

        String playerName1 = matchDTO1.getPlayer1().getName();
        System.out.println(playerName);
        System.out.println(onGoingMatchService.isMatchExist(uuid));

    }
}
