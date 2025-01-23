package main;

import algorithm.ExpectMinMax;
import game.*;

import java.util.Scanner;

public class Play {


    int diceResult;

    public Play() {

        Dice dice = new Dice();

        Game game = new Game(new Board());

        Scanner scanner = new Scanner(System.in);


        while (!game.board.checkWinning()) {

            diceResult = dice.rollDice();


            game.board.printBoard();

            System.out.println(game.board.players[game.currentPlayerIndex].getRole() + "'s turn");


//            diceResult = scanner.nextInt();
            if (game.board.players[game.currentPlayerIndex].getRole() == Role.CPU)
                System.out.println("CPU roll result is : " + diceResult);
            else
                System.out.println("Your roll result is : " + diceResult);

            int option;
            boolean canMove;


            do {
                ExpectMinMax AIPlayer = new ExpectMinMax(game);


//                var p = game.possibleMoves(diceResult);// { cheat
//                if (p.size() == 1) {
//                    option = p.get(0);
//                } else {
                if (game.board.players[game.currentPlayerIndex].getRole() == Role.Player) {
                    System.out.print("Which token do you want to play? \n Your options :     ");
                    System.out.println(game.possibleMoves(diceResult));
                    option = scanner.nextInt();
                } else {
                    int bestOption = AIPlayer.bestOption(game.possibleMoves(diceResult));

                    System.out.println("CPU choose:\t" + bestOption + "\nfrom his options:\t" + game.possibleMoves(diceResult));

                    option = bestOption;
                }
//                }

                canMove = game.canMove(option, diceResult, false);
                if (!canMove) {
                    System.out.println("You can't play this move?");
                }
            } while (!canMove);

            game.playMove(diceResult, option);


        }
        for (Player player : game.board.players) {
            if (player.emptyWiningBlocks() == 0) {
                System.out.println("!!!___" + player.getRole() + " Wins ___!!!");
                break;
            }
        }
        game.board.printBoard();

    }

}
