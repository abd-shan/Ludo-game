package algorithm;

import game.Game;
import game.Player;

import java.util.ArrayList;

public class Expectiminmax {
    Game game;
    final static int DEPTH = 3;
    public Expectiminmax(Game game) {
        this.game = game;
    }


    public int bestOption(ArrayList<Integer> possibleOptions) {
        double bestScore= Double.NEGATIVE_INFINITY;
        int bestOp=-1;

        for (int option : possibleOptions) {
            Double totalScore=0.0;

            for (int i=1;i<=6;i++){
                Game game =simulateGame(option,i,this.game);



            }
//
        }

        return possibleOptions.get(0);
    }

    private double expectiminmax(Integer option){
        return 0.0;
    }

    public int scoreGame(Game game, int option, int ResultDice, Player player) {
        int score = 0;
        if (game.canMove(option,ResultDice,true)){
            score++;
            if (game.board.checkWinning()) {
                score += Integer.MAX_VALUE;
                return score;
            }
            if (player.emptyWiningBlocks()<4)
                score+=(4-player.emptyWiningBlocks())*100;

            for (int token:player.getTokens()){
                if (isSave(token))
                    score+=5;
            }

            return score;
        }
        return 0;
    }

    boolean isSave(int token){
        return token == 22 || token == 9;
    }

    public Game simulateGame(int option,int diceResult,Game game) {
        Game newGame=game.clone();
        newGame.playMove(option,diceResult);
        return newGame;
    }


}
