package com.slava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchDTO {
    private Long id;
    private PlayerDTO player1;
    private PlayerDTO player2;
    private PlayerDTO winner;
    private int player1GamesWon;
    private int player2GamesWon;
    private int player1SetWon;
    private int player2SetWon;
    private String player1CurrentScore;
    private String player2CurrentScore;
    private boolean isDeuce;
    private boolean isFinished;
}
