package main;

import algorithm.ExpectMinMax;
import game.Board;
import game.Dice;
import game.Game;
import game.Role;

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
            if (game.board.players[game.currentPlayerIndex].getRole()== Role.CPU)
            System.out.println("CPU roll result is : " + diceResult);
            else
                System.out.println("Your roll result is : " + diceResult);

            int option;
            boolean canMove;


            do {
                ExpectMinMax AIPlayer = new ExpectMinMax(game);



                if (game.board.players[game.currentPlayerIndex].getRole()== Role.Player) {
                    System.out.print("Which token do you want to play? \n Your options :     ");
                    System.out.println(game.possibleMoves(diceResult));
                    option = scanner.nextInt();
                }
                else {
                    System.out.println("CPU choose:    " + AIPlayer.bestOption(game.possibleMoves(diceResult)) +
                            "\nfrom his options:    " + game.possibleMoves(diceResult));
//                    System.out.println("the score:  "+AIPlayer.evaluateGame(game));

                    option=AIPlayer.bestOption(game.possibleMoves(diceResult));
                }
//                var p=game.possibleMoves(diceResult);// { cheat
//                if (p.size() == 1) {
//                    option = p.get(0);
//                } else



                canMove = game.canMove(option, diceResult, false);
                if (!canMove) {
                    System.out.println("You can't play this move?");
                }
            } while (!canMove);

            game.playMove(diceResult, option);


        }


    }

}
