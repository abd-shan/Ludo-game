package game;

import java.util.ArrayList;

public class Game {
    public Board board;
    public int currentPlayerIndex;
    public Game father;

    public Game(Board board) {
        this.board = board;
        lastPlayer = board.players[0];
    }

    int row = 0;
    Player lastPlayer;

    public boolean playMove(int diceResult, int option) {

        if (!canMove(option, diceResult, true)) {
            if (canMove(1, diceResult, true) || canMove(2, diceResult, true) || canMove(3, diceResult, true) || canMove(4, diceResult, true)) {
                return false;
            }
            if (option == 1)
                switchPlayer();
            return false;
        }


        Player currentPlayer = board.players[currentPlayerIndex];
        Player opponentPlayer = board.players[(currentPlayerIndex + 1) % 2];


        if (currentPlayer.role != lastPlayer.role) {
            row = 0;
            lastPlayer = currentPlayer;
        }

        if (diceResult == 6)
            row++;


        int tokenIndex = option - 1;

        if (currentPlayer.getToken(0) == -1 && diceResult != 6) {
            switchPlayer();
            return false;
        }


        int indexBefore = currentPlayer.getToken(tokenIndex);
        int indexAfter;
        if (indexBefore == -1) {
            indexAfter = currentPlayer.startIndex;

            boolean kill = false;
            if (board.track.get(indexAfter).getOwner() == opponentPlayer.role) {
                opponentPlayer.sendHome(indexAfter);
                kill = true;
            }

            currentPlayer.sendTo(indexBefore, indexAfter);
            board.track.get(indexAfter).updateSquare(board.players);

            if ((diceResult != 6 && !kill) || row == 3) {
                switchPlayer();
            }
            return true;
        }


        if (indexBefore + diceResult == currentPlayer.endIndex + currentPlayer.emptyWiningBlocks() && indexBefore <= currentPlayer.endIndex) {
            indexAfter = indexBefore + diceResult;
            ArrayList<Square> winingBlocks;

            if (currentPlayerIndex == 0) {
                winingBlocks = board.playerWiningBlocks;
                int i;
                for (i = 0; i < 4; i++) {
                    if (winingBlocks.get(i).isEmpty()) {
                        indexAfter = 41 + i;
                        break;
                    }
                }
                currentPlayer.sendTo(indexBefore, indexAfter);
                board.track.get(indexBefore).updateSquare(board.players);
                winingBlocks.get(i).updateSquare(board.players);
            } else if (currentPlayerIndex == 1) {
                winingBlocks = board.cpuWiningBlocks;
                int i;
                for (i = 0; i < 4; i++) {
                    if (winingBlocks.get(i).isEmpty()) {
                        indexAfter = 51 + i;
                        break;
                    }
                }
                currentPlayer.sendTo(indexBefore, indexAfter);
                board.track.get(indexBefore).updateSquare(board.players);
                winingBlocks.get(i).updateSquare(board.players);
            }

            if (diceResult != 6 || row == 3) {
                switchPlayer();
            }
            return true;
        }


        indexAfter = (indexBefore + diceResult) % 26;
        boolean kill = false;
        if (board.track.get(indexAfter).getOwner() == opponentPlayer.role) {
            opponentPlayer.sendHome(indexAfter);
            kill = true;
        }

        currentPlayer.sendTo(indexBefore, indexAfter);
        board.track.get(indexBefore).updateSquare(board.players);
        board.track.get(indexAfter).updateSquare(board.players);

        if ((diceResult != 6 && !kill) || row == 3) {
            switchPlayer();
        }
        return true;
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    public boolean canMove(int option, int diceResult, boolean really) { //error

        if (!really && option == 1 && !canMove(2, diceResult, true) && !canMove(3, diceResult, true) && !canMove(4, diceResult, true))
            return true;


        if (option < 1 || option > 4)
            return false;

        if (option > 1 && howManyTokensOut() == 4)
            return false;


        int indexBefore = board.players[currentPlayerIndex].tokens[option - 1];
        if (indexBefore > 30)
            return false;


        Player currentPlayer = board.players[currentPlayerIndex];
        if (indexBefore + diceResult == currentPlayer.endIndex + currentPlayer.emptyWiningBlocks() && indexBefore <= currentPlayer.endIndex) {
            return true;
        }




            indexBefore = board.players[currentPlayerIndex].tokens[option - 1];
        if (indexBefore != -1) {
            int indexAfter = indexBefore + diceResult;

            for (int i = indexBefore + 1; i < indexAfter; i++) {
                try {
                    if (board.track.get(i).isWall())
                        return false;
                } catch (Exception e) {
                    i = 0;
                    indexAfter -= 26;
                }
            }

        }


        int indexAfter = (indexBefore + diceResult) % 26;
        if (indexBefore == -1)
            indexAfter = board.players[currentPlayerIndex].startIndex;
        Square after = board.track.get(indexAfter);
        if (after.isSpecial && !after.isEmpty() && after.getOwner() != board.players[currentPlayerIndex].role)
            return false;


        if (option + howManyTokensOut() > 4) {
            if (option + howManyTokensOut() == 5 && diceResult == 6)
                return true;
            return false;
        }


        return true;
    }

    private int howManyTokensOut() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (board.players[currentPlayerIndex].tokens[i] == -1)
                count++;
        }
        return count;
    }

    public boolean areTherePossibleMoves(int diceResult) {
        if (possibleMoves(diceResult).isEmpty())
            return false;
        return true;
    }

    public ArrayList<Integer> possibleMoves(int diceResult) {

        ArrayList<Integer> possibleMoves = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            if (canMove(i, diceResult, false))
                possibleMoves.add(i);
        }
        return possibleMoves;
    }

    public Game clone() {
        Game clonedGame = new Game(this.board.clone());

        clonedGame.currentPlayerIndex = this.currentPlayerIndex;
        clonedGame.row = this.row;

        clonedGame.lastPlayer = this.lastPlayer.clone();

        return clonedGame;
    }

}
