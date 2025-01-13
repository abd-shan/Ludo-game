package game;

import java.util.Stack;

public class Game {
    public Board board;

    public int currentPlayerIndex = 0;

    public Game(Board board) {
        this.board = board;
    }

    public void playMove(int diceResult, int option) {
        Player currentPlayer = board.players[currentPlayerIndex];

        Token currentToken = currentPlayer.tokens[option - 1];


        // cannot move
        if (currentToken.currentIndex == -1 && diceResult != 6) {
            switchPlayer();
            currentPlayer.sortTokens();
            return;
        }

        int indexBefore = currentToken.currentIndex;
        int indexAfter; //moving from start index
        if (indexBefore == -1) {
            indexAfter = currentPlayer.startIndex;
            currentToken.currentIndex = indexAfter;
            board.track.get(indexAfter).addToken(board.players[currentPlayerIndex].role, currentToken);
            currentPlayer.sortTokens();
            return;
        }


        //enter winning zone
        if (indexBefore + diceResult == currentPlayer.endIndex + currentPlayer.emptyWiningBlocks && indexBefore <= currentPlayer.endIndex) {
            currentToken = board.track.get(indexBefore).removeToken(currentPlayer.endIndex + currentPlayer.emptyWiningBlocks);
            currentToken.currentIndex = 30;
            currentPlayer.emptyWiningBlocks--;
            Stack<Square> r;
            if (currentPlayer.role == Role.Player)
                r = board.playerWiningBlocks;
            else
                r = board.cpuWiningBlocks;


            for (int i = 0; i < 4; i++) {
                if (r.get(i).isEmpty) {
                    r.get(i).isEmpty = false;
                    break;
                }
            }
            return;
        }

        //regular moving
        indexAfter = (indexBefore + diceResult) % 26;
        currentToken = board.track.get(indexBefore).removeToken(indexAfter);//
//        currentToken.currentIndex = indexAfter;
        board.track.get(indexAfter).addToken(board.players[currentPlayerIndex].role, currentToken);


        if (diceResult != 6) {
            switchPlayer();
        }


        currentPlayer.sortTokens();
        return;
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }


    public boolean canMove(int option, int diceResult) {
        if (option < 1 || option > 4)
            return false;

        if (option > 1 && howManyTokensOut() == 4)
            return false;

//        {
//            int currentTokenIndex = board.players[currentPlayerIndex].tokens[option - 1].currentIndex;
//            if (currentTokenIndex == -1 && diceResult != 6) {
////                switchPlayer();
//                return false;
//            }
//        }


        {
            int currentTokenIndex = board.players[currentPlayerIndex].tokens[option - 1].currentIndex;
            if (currentTokenIndex == 30)
                return false;
        }


        {
            int indexBefore = board.players[currentPlayerIndex].tokens[option - 1].currentIndex;
            if (indexBefore != -1) {
                int indexAfter = indexBefore + diceResult;

                for (int i = indexBefore + 1; i < indexAfter; i++) {
                    try {
                        if (board.track.get(i).isWall)
                            return false;
                    } catch (Exception e) {
                        i = 0;
                        indexAfter -= 26;
                    }
                }

            }


        }

        {
            int indexBefore = board.players[currentPlayerIndex].tokens[option - 1].currentIndex;
            int indexAfter = (indexBefore + diceResult) % 26;
            Square after = board.track.get(indexAfter);
            if (after.isSpecial && !after.isEmpty && after.getOwner() != board.players[currentPlayerIndex].role)
                return false;
        }



        if (option == 1 && howManyTokensOut() == 4)
            return true;

        if (option + howManyTokensOut() > 4) {
            if (option + howManyTokensOut() == 5 && diceResult == 6)
                return true;
            return false;
        }

        return true;
    }

    public boolean areTherePossibleMoves(int diceResult) {
        for (int i = 1; i <= 4; i++) {
            if (canMove(i, diceResult))
                return true;
        }
        return false;
    }


    private int howManyTokensOut() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (board.players[currentPlayerIndex].tokens[i].currentIndex == -1)
                count++;
        }
        return count;
    }

}
