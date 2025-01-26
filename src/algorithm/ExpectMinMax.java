package algorithm;

import game.Game;
import game.Player;
import game.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExpectMinMax {
    Game game;
    final static int DEPTH = 3;

    public ExpectMinMax(Game game) {
        this.game = game;
    }


    public int bestOption(ArrayList<Integer> possibleMoves) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int bestMove = -1;

        for (int move : possibleMoves) {
            double totalScore = 0.0;


            for (int dice = 1; dice <= 6; dice++) {
                Game simulatedGame = simulateGame(move, dice, game);
                double minMax = expectiminimax(simulatedGame, dice, DEPTH, true, move);
                double score = (1.0 / 6) * minMax;
                totalScore += score;
//                System.out.println(minMax);
            }

            if (totalScore > bestScore) {
                bestScore = totalScore;
                bestMove = move;
            }
//            System.out.println("totalScore: " + totalScore);
        }
//        System.out.println("bestMove: " + bestScore);

        return bestMove;
    }


    private double expectiminimax(Game game, int resultDice, int depth, boolean isChance, int option) {
        if (depth == 0 || game.board.checkWinning()) {
            return heuristic(game);
        }

        if (isChance) {
            double expectedValue = 0.0;

            for (int diceRoll = 1; diceRoll <= 6; diceRoll++) {
                double probability = 1.0 / 6;
                Game childGame = simulateGame(option, diceRoll, game);

//                    String nextNodeType = (game.board.players[game.currentPlayerIndex].getRole() == Role.Player) ? "Min" : "Max";
                expectedValue += probability * expectiminimax(childGame, resultDice, depth - 1, false, option);
            }

            return expectedValue;
        } else {
            if (game.board.players[game.currentPlayerIndex].getRole() == Role.Player) {
                double worstValue = Double.POSITIVE_INFINITY;

                for (int move : game.possibleMoves(resultDice)) {
                    Game childNode = simulateGame(move, resultDice, game);
                    worstValue = Math.min(worstValue, expectiminimax(childNode, resultDice, depth - 1, true, move));
                }

                return worstValue;
            } else {
                double bestValue = Double.NEGATIVE_INFINITY;

                for (int move : game.possibleMoves(resultDice)) {
                    Game childGame = simulateGame(move, resultDice, game);
                    bestValue = Math.max(bestValue, expectiminimax(childGame, resultDice, depth - 1, true, move));
                }

                return bestValue;
            }
        }
    }


    public double heuristic(Game game) {
        double score = 0;

        Player currentPlayer = game.board.players[game.currentPlayerIndex];
        score += scorePlayer(currentPlayer);

        Player opponuntPlayer = game.board.players[(game.currentPlayerIndex + 1) % 2];
        score -= scorePlayer(opponuntPlayer);

        return score;
    }

    private double scorePlayer(Player player) {
        double score = 0;

        if (player.emptyWiningBlocks() == 0)
            score += 100000;


//        if (player.getRole() == Role.Player) {
//
//            for (int token : player.getTokens()) {
////                if (token > 0) score += 100;
//
////                if (token>=23 - (4-player.emptyWiningBlocks()) && token<=25)
////                    score +=90;
//
//                if (isSave(token))
//                    score += 10;
//
//            }
//        } else
        if (player.getRole() == Role.CPU) {
            for (int token : player.getTokens()) {

                if (token > 0) score += 1000;

//                if (token>=10 - (4-player.emptyWiningBlocks()) && token<=12)
//                    score +=399;
                if (token > 8 && token < 13)
                    score += token * 100;

//                if (token >= 13)
//                    score -= 5000;


                if (isSave(token))
                    score += 10;

                score += (4 - player.emptyWiningBlocks()) * 5000;


            }

        }

        return score;
    }

    boolean isWall(int[] tokens) {
        Set<Integer> set = new HashSet<>();
        for (int token : tokens) {
            if (set.contains(token))
                return true;
            set.add(token);
        }
        return false;
    }

    boolean isSave(int token) {
        return token == 22 || token == 9;
    }

    public Game simulateGame(int option, int diceResult, Game game) {
        Game newGame = game.clone();
        newGame.playMove(option, diceResult);
        return newGame;
    }


}
