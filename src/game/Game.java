package game;

import java.util.ArrayList;
import java.util.Stack;

public class Game {
    public Board board;
    public int currentPlayerIndex;

    public Game(Board board) {
        this.board = board;
        lastPlayer = board.players[0];
    }

    int row = 0;
    Player lastPlayer;

    public void playMove(int diceResult, int option) {


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
            return;
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
            return;
        }


        if (indexBefore + diceResult == currentPlayer.endIndex + currentPlayer.emptyWiningBlocks() && indexBefore <= currentPlayer.endIndex) {
            indexAfter = indexBefore + diceResult;
            Stack<Square> winingBlocks;

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
            return;
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
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    public boolean canMove(int option, int diceResult) {

        if (option < 1 || option > 4)
            return false;

        if (option > 1 && howManyTokensOut() == 4)
            return false;


        int indexBefore = board.players[currentPlayerIndex].tokens[option - 1];
        if (indexBefore > 30)
            return false;


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
        Square after = board.track.get(indexAfter);
        if (after.isSpecial && !after.isEmpty() && after.getOwner() != board.players[currentPlayerIndex].role)
            return false;


        if (option == 1 && howManyTokensOut() == 4)
            return true;

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
            if (canMove(i, diceResult))
                possibleMoves.add(i);
        }
        return possibleMoves;
    }

}
