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
//            diceResult = scanner.nextInt();

            if (!game.areTherePossibleMoves(diceResult))
            {
                game.switchPlayer();
                continue;
            }


            game.board.printBoard();

            System.out.println(game.board.players[game.currentPlayerIndex].role + "'s turn");


            System.out.println("Your roll result is : " + diceResult);

            int option;
            boolean canMove;


            do {

                String moves="Which token do you want to play? \n Your options :     ";
                if (game.canMove(1,diceResult))
                    moves+="1  ";
                if (game.canMove(2,diceResult))
                    moves+="2  ";
                if (game.canMove(3,diceResult))
                    moves+="3  ";
                if (game.canMove(4,diceResult))
                    moves+="4  ";

                System.out.println(moves);
                option = scanner.nextInt();


                canMove = game.canMove(option, diceResult);
                if (!canMove) {
                    System.out.println("You can't play this move?");
                }
            } while (!canMove);

            game.playMove(diceResult, option);


        }


    }

}
