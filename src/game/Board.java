package game;

import java.util.ArrayList;
import java.util.Stack;

public class Board {

    ArrayList<Square> track = new ArrayList<>(26);

    public Player[] players = {new Player(Role.Player, 1, 25), new Player(Role.CPU, 14, 12)};

    Stack<Square> playerWiningBlocks = new Stack<>();
    Stack<Square> cpuWiningBlocks = new Stack<>();


    public Board() {
        for (int i = 0; i < 26; i++) {

            Square square;

            if (i == 1 || i == 14)
                square = new Square(true, true, false);
            else if (i == 9 || i == 22)
                square = new Square(false, true, false);
            else
                square = new Square(false, false, false);

            track.add(square);
        }

        for (int i = 0; i < 4; i++) {
            Square playerSquare = new Square(false, false, true);
            playerSquare.setOwner(Role.Player);
            playerWiningBlocks.add(playerSquare);


            Square cpuSquare = new Square(false, false, true);
            cpuSquare.setOwner(Role.CPU);
            cpuWiningBlocks.add(cpuSquare);
        }

    }

    public void printBoard() {
        for (int i = 0; i < 12; i++) {
            System.out.print(track.get(i));
        }

        System.out.println();

        System.out.print(track.get(25));

        for (int i = 3; i >= 0; i--) {
            System.out.print(playerWiningBlocks.get(i));
        }

        System.out.print("▶️");
        System.out.print("◀️");

        for (int i = 0; i < 4; i++) {
            System.out.print(cpuWiningBlocks.get(i));
        }


        System.out.print(track.get(12));

        System.out.println();

        for (int i = 24; i > 12; i--) {
            System.out.print(track.get(i));
        }

        System.out.println();

        for (int i = 0; i < 4; i++) {
            if (this.players[0].tokens[i].currentIndex == -1)
                System.out.print("🔴");
        }
        System.out.println();
        for (int i = 0; i < 4; i++) {
            if (this.players[1].tokens[i].currentIndex == -1)
                System.out.print("🔵");
        }
        System.out.println();
    }

    public boolean checkWinning(){
        Player player=players[0];

        if (player.emptyWiningBlocks==0) {
            System.out.println("!!!___ Player Wins ___!!!");
            printBoard();
            return true;
        }
        Player cpu=players[1];

        if (cpu.emptyWiningBlocks==0) {
            System.out.println("!!!___ CPU Wins ___!!!");
            printBoard();
            return true;
        }
        return false;
    }


    protected Board clone() {
        Board clonedBoard = new Board();

        clonedBoard.track = new ArrayList<>();
        for (Square square : this.track) {
            clonedBoard.track.add(square.clone());
        }

        clonedBoard.players = new Player[this.players.length];
        for (int i = 0; i < this.players.length; i++) {
            clonedBoard.players[i] = this.players[i].clone();
        }

        clonedBoard.playerWiningBlocks = new Stack<>();
        for (Square square : this.playerWiningBlocks) {
            clonedBoard.playerWiningBlocks.add(square.clone());
        }

        clonedBoard.cpuWiningBlocks = new Stack<>();
        for (Square square : this.cpuWiningBlocks) {
            clonedBoard.cpuWiningBlocks.add(square.clone());
        }

        return clonedBoard;
    }


}
