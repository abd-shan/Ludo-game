package main;

import game.Board;
import game.Dice;
import game.Game;

import java.util.Scanner;

public class Play {


    int diceResult;

    public Play() {

        Dice dice = new Dice();

        Game game = new Game(new Board());

        Scanner scanner = new Scanner(System.in);


        while ( !game.board.checkWinning()) {

            diceResult = dice.rollDice();


            game.board.printBoard();

            System.out.println(game.board.players[game.currentPlayerIndex].getRole() + "'s turn");


//            diceResult = scanner.nextInt();
            System.out.println("Your roll result is : " + diceResult);

            int option;
            boolean canMove;


            do {


                System.out.print("Which token do you want to play? \n Your options :     ");

                System.out.println(game.possibleMoves(diceResult));
                option = scanner.nextInt();


                canMove = game.canMove(option, diceResult,false);
                if (!canMove) {
                    System.out.println("You can't play this move?");
                }
            } while (!canMove);

            game.playMove(diceResult, option);


        }


    }

}
