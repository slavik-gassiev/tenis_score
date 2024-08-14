package com.slava.service;

import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameService {

    public void playerPoint(MatchDTO match, PlayerDTO score) {
        if (match.isFinished()) {
            log.info("Match is already finished. No more points can be played.");
            return;
        }

        if (score.equals(match.getPlayer1())) {
            updateScore(match, true);
        } else if (score.equals(match.getPlayer2())) {
            updateScore(match, false);
        }

        checkSetWinner(match);
        checkMatchWinner(match);
    }

    private void updateScore(MatchDTO match, boolean isPlayer1) {
        String currentScore = isPlayer1 ? match.getPlayer1CurrentScore() : match.getPlayer2CurrentScore();
        String opponentScore = isPlayer1 ? match.getPlayer2CurrentScore() : match.getPlayer1CurrentScore();

        switch (currentScore) {
            case "0":
                currentScore = "15";
                break;
            case "15":
                currentScore = "30";
                break;
            case "30":
                currentScore = "40";
                break;
            case "40":
                if ("40".equals(opponentScore)) {
                    match.setDeuce(true);
                    currentScore = "AD";
                } else {
                    winGame(match, isPlayer1);
                    return;
                }
                break;
            case "AD":
                winGame(match, isPlayer1);
                return;
            case "tie-break":
                winTieBreak(match, isPlayer1);
                return;

        }

        if (isPlayer1) {
            match.setPlayer1CurrentScore(currentScore);
        } else {
            match.setPlayer2CurrentScore(currentScore);
        }
    }

    private void winGame(MatchDTO match, boolean isPlayer1) {
        if (isPlayer1) {
            match.setPlayer1GamesWon(match.getPlayer1GamesWon() + 1);
            log.info("Players 1 wins the game. Current score: {}-{}"
                    , match.getPlayer1GamesWon(), match.getPlayer2GamesWon());
        } else {
            match.setPlayer2GamesWon(match.getPlayer2GamesWon() + 1);
            log.info("Players 2 wins the game. Current score: {}-{}",
                    match.getPlayer2GamesWon(), match.getPlayer1GamesWon());
        }
        resetScores(match);
    }

    private void checkTieBreakWinner(MatchDTO match) {
        if (match.getPlayer1TieBreaksWon() >= 7 && match.getPlayer2TieBreaksWon() - match.getPlayer2TieBreaksWon() >= 2) {
            match.setPlayer1SetWon(match.getPlayer1SetWon() + 1);
            resetGameWins(match);
            resetScores(match);
            match.setTieBreak(false);
        } else if (match.getPlayer2TieBreaksWon() >= 7 && match.getPlayer2TieBreaksWon() - match.getPlayer2TieBreaksWon() >= 2) {
            match.setPlayer2GamesWon(match.getPlayer2GamesWon() + 1);
            resetGameWins(match);
            resetScores(match);
            match.setTieBreak(false);
        }
    }

    private void winTieBreak(MatchDTO match, boolean isPlayer1) {
        if (isPlayer1) {
            match.setPlayer1TieBreaksWon(match.getPlayer1TieBreaksWon() + 1);
            checkTieBreakWinner(match);
        } else {
            match.setPlayer2TieBreaksWon(match.getPlayer2TieBreaksWon() + 1);
            checkTieBreakWinner(match);
        };
    }

    private void checkSetWinner(MatchDTO match) {
        if (match.getPlayer1GamesWon() >= 6 && match.getPlayer1GamesWon() - match.getPlayer2GamesWon() >= 2) {
            match.setPlayer1SetWon(match.getPlayer1SetWon() + 1);
            resetGameWins(match);
        } else if (match.getPlayer2GamesWon() >= 6 && match.getPlayer2GamesWon() - match.getPlayer1GamesWon() >= 2) {
            match.setPlayer2GamesWon(match.getPlayer2GamesWon() + 1);
            resetGameWins(match);
        } else if (match.getPlayer1GamesWon() == 6 && match.getPlayer2GamesWon() == 6) {
            resetGameWins(match);
            match.setTieBreak(true);
            match.setPlayer1CurrentScore("tie-break");
        }
    }

    private void checkMatchWinner(MatchDTO match) {
        if (match.getPlayer1SetWon() == 2) {
            match.setWinner(match.getPlayer1());
            match.setFinished(true);
            log.info("Player 1 wins the match!");
        } else {
            match.setWinner(match.getPlayer2());
            match.setFinished(true);
            log.info("Player 2 wins the match!");
        }
    }

    private void resetScores(MatchDTO match) {
        match.setPlayer1CurrentScore("0");
        match.setPlayer1CurrentScore("0");
        match.setDeuce(false);
    }

    private void resetGameWins(MatchDTO match) {
        match.setPlayer1GamesWon(0);
        match.setPlayer2GamesWon(0);
    }

    private void resetTieBreaks(MatchDTO match) {
        match.setPlayer1TieBreaksWon(0);
        match.setPlayer2TieBreaksWon(0);
    }
}
