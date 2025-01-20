package game;

import java.util.ArrayList;

public class Board {

    ArrayList<Square> track = new ArrayList<>(26);

    public Player[] players = {new Player(Role.Player, 1, 25), new Player(Role.CPU, 14, 12)};

    ArrayList<Square> playerWiningBlocks = new ArrayList<>();
    ArrayList<Square> cpuWiningBlocks = new ArrayList<>();


    public Board() {
        for (int i = 0; i < 26; i++) {

            Square square;

            if (i == 1 || i == 14)
                square = new Square(i, true, true, false);
            else if (i == 9 || i == 22)
                square = new Square(i, false, true, false);
            else
                square = new Square(i, false, false, false);

            track.add(square);
        }

        for (int i = 0; i < 4; i++) {
            Square playerSquare = new Square(41 + i, false, false, true);
            playerSquare.setOwner(Role.Player);
            playerWiningBlocks.add(playerSquare);


            Square cpuSquare = new Square(51 + i, false, false, true);
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
            if (this.players[0].tokens[i] == -1)
                System.out.print("🔴");
        }
        System.out.println();
        for (int i = 0; i < 4; i++) {
            if (this.players[1].tokens[i] == -1)
                System.out.print("🔵");
        }
        System.out.println();
    }

    public boolean checkWinning() {
        Player player = players[0];

        if (player.emptyWiningBlocks() == 0) {
            System.out.println("!!!___ Player Wins ___!!!");
            printBoard();
            return true;
        }
        Player cpu = players[1];

        if (cpu.emptyWiningBlocks() == 0) {
            System.out.println("!!!___ CPU Wins ___!!!");
            printBoard();
            return true;
        }
        return false;
    }

    public Board clone() {
        Board cloned = new Board();


        cloned.players = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            cloned.players[i] = players[i].clone();
        }

        cloned.track = new ArrayList<>(track.size());
        for (Square square : track) {
            cloned.track.add(square.clone());
        }

        cloned.playerWiningBlocks = new ArrayList<>(playerWiningBlocks.size());
        for (Square square : playerWiningBlocks) {
            cloned.playerWiningBlocks.add(square.clone());
        }

        cloned.cpuWiningBlocks = new ArrayList<>(cpuWiningBlocks.size());
        for (Square square : cpuWiningBlocks) {
            cloned.cpuWiningBlocks.add(square.clone());
        }

        return cloned;
    }

}
