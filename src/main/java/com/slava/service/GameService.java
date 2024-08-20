package com.slava.service;

import com.slava.dto.MatchDTO;
import com.slava.dto.PlayerDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameService {

    private OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();
    private volatile MatchDTO match;

    public void playerPoint(String uuid, PlayerDTO score) {
        match = onGoingMatchService.getMatch(uuid);


        if (match.isFinished()) {
            log.info("Match is already finished. No more points can be played.");
            return;
        }

        if (score.equals(match.getPlayer1())) {
            updateScore(uuid, match, true);
        } else if (score.equals(match.getPlayer2())) {
            updateScore(uuid, match, false);
        }

        checkDeuce(uuid, match);
        checkTieBreakWinner(uuid, match);
        checkSetWinner(uuid, match);
        checkMatchWinner(uuid, match);
        int x = 1;
    }

    private synchronized void updateScore(String uuid, MatchDTO match, boolean isPlayer1) {
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
                    currentScore = "deuce";
                    opponentScore = "deuce";
                } else {
                    winGame(uuid,match, isPlayer1);
                    return;
                }
                break;
            case "deuce":
                winDeuce(uuid, match, isPlayer1);
                break;
            case "tie-break":
                winTieBreak(uuid, match, isPlayer1);
                return;

        }

        if (isPlayer1) {
            match.setPlayer1CurrentScore(currentScore);
            match.setPlayer2CurrentScore(opponentScore);
            onGoingMatchService.updateMatch(uuid, match);
        } else {
            match.setPlayer2CurrentScore(currentScore);
            match.setPlayer1CurrentScore(opponentScore);
            onGoingMatchService.updateMatch(uuid, match);
        }
    }

    private synchronized void winDeuce(String uuid, MatchDTO match, boolean isPlayer1) {
        if (isPlayer1) {
            match.setPlayer1CurrentDeuce(match.getPlayer1CurrentDeuce() + 1);
            onGoingMatchService.updateMatch(uuid, match);

        }   else {
            match.setPlayer2CurrentDeuce(match.getPlayer2CurrentDeuce() + 1);
            onGoingMatchService.updateMatch(uuid, match);

        }
    }

    private synchronized void checkDeuce(String uuid, MatchDTO match) {
        if (match.getPlayer1CurrentDeuce() >= 2 && match.getPlayer1CurrentDeuce() - match.getPlayer2CurrentDeuce() >= 2) {
            match.setPlayer1GamesWon(match.getPlayer1GamesWon() + 1);
            resetDeuce(uuid, match);
            resetScores(uuid, match);
        } else if (match.getPlayer2CurrentDeuce() >= 2 && match.getPlayer2CurrentDeuce() - match.getPlayer1CurrentDeuce() >= 2) {
            match.setPlayer2GamesWon(match.getPlayer2GamesWon() + 1);
            resetDeuce(uuid, match);
            resetScores(uuid, match);
        }
    }

    private synchronized void winGame(String uuid, MatchDTO match, boolean isPlayer1) {
        if (isPlayer1) {
            match.setPlayer1GamesWon(match.getPlayer1GamesWon() + 1);
            onGoingMatchService.updateMatch(uuid, match);
            log.info("Players 1 wins the game. Current score: {}-{}"
                    , match.getPlayer1GamesWon(), match.getPlayer2GamesWon());
        } else {
            match.setPlayer2GamesWon(match.getPlayer2GamesWon() + 1);
            onGoingMatchService.updateMatch(uuid, match);
            log.info("Players 2 wins the game. Current score: {}-{}",
                    match.getPlayer2GamesWon(), match.getPlayer1GamesWon());
        }
        resetScores(uuid, match);
    }

    private synchronized void winTieBreak(String uuid, MatchDTO match, boolean isPlayer1) {
        if (isPlayer1) {
            match.setPlayer1TieBreaksWon(match.getPlayer1TieBreaksWon() + 1);
            onGoingMatchService.updateMatch(uuid, match);

        } else {
            match.setPlayer2TieBreaksWon(match.getPlayer2TieBreaksWon() + 1);
            onGoingMatchService.updateMatch(uuid, match);
        };
    }

    private synchronized void checkTieBreakWinner(String uuid, MatchDTO match) {
        if (match.getPlayer1TieBreaksWon() >= 7 && match.getPlayer1TieBreaksWon() - match.getPlayer2TieBreaksWon() >= 2) {
            match.setTieBreak(false);
            match.setPlayer1SetWon(match.getPlayer1SetWon() + 1);
            resetGameWins(uuid, match);
            resetScores(uuid, match);
        } else if (match.getPlayer2TieBreaksWon() >= 7 && match.getPlayer2TieBreaksWon() - match.getPlayer1TieBreaksWon() >= 2) {
            match.setTieBreak(false);
            match.setPlayer2GamesWon(match.getPlayer2GamesWon() + 1);
            resetGameWins(uuid, match);
            resetScores(uuid, match);
        }
    }

    private synchronized void checkSetWinner(String uuid, MatchDTO match) {
        if (match.getPlayer1GamesWon() >= 6 && match.getPlayer1GamesWon() - match.getPlayer2GamesWon() >= 2) {
            match.setPlayer1SetWon(match.getPlayer1SetWon() + 1);
            resetGameWins(uuid, match);
        } else if (match.getPlayer2GamesWon() >= 6 && match.getPlayer2GamesWon() - match.getPlayer1GamesWon() >= 2) {
            match.setPlayer2SetWon(match.getPlayer2SetWon() + 1);
            resetGameWins(uuid, match);
        } else if (match.getPlayer1GamesWon() == 6 && match.getPlayer2GamesWon() == 6) {
            match.setTieBreak(true);
            match.setPlayer1CurrentScore("tie-break");
            match.setPlayer2CurrentScore("tie-break");
            resetGameWins(uuid, match);
        }
    }

    private synchronized void checkMatchWinner(String uuid, MatchDTO match) {
        if (match.getPlayer1SetWon() >= 2 && match.getPlayer1SetWon() - match.getPlayer2SetWon() >= 2) {
            match.setWinner(match.getPlayer1());
            match.setFinished(true);
            onGoingMatchService.updateMatch(uuid, match);
            log.info("Player 1 wins the match!");
        } else if (match.getPlayer2SetWon() >= 2 && match.getPlayer2SetWon() - match.getPlayer1SetWon() >= 2){
            match.setWinner(match.getPlayer2());
            match.setFinished(true);
            onGoingMatchService.updateMatch(uuid, match);
            log.info("Player 2 wins the match!");
        }
    }

    private synchronized void resetScores(String uuid, MatchDTO match) {
        match.setPlayer1CurrentScore("0");
        match.setPlayer2CurrentScore("0");
        match.setDeuce(false);
        onGoingMatchService.updateMatch(uuid, match);
    }

    private void resetDeuce(String uuid, MatchDTO match) {
        match.setPlayer1CurrentDeuce(0);
        match.setPlayer2CurrentDeuce(0);
        onGoingMatchService.updateMatch(uuid, match);
    }

    private synchronized void resetGameWins(String uuid, MatchDTO match) {
        match.setPlayer1GamesWon(0);
        match.setPlayer2GamesWon(0);
        onGoingMatchService.updateMatch(uuid, match);
    }

    private synchronized void resetTieBreaks(MatchDTO match) {
        match.setPlayer1TieBreaksWon(0);
        match.setPlayer2TieBreaksWon(0);
    }
}
