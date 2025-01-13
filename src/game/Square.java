package game;

import java.util.ArrayList;

public class Square {

    final boolean isStart;
    final boolean isSpecial;
    final boolean isWinning;

    boolean isEmpty = true;
    boolean isWall;


    private int tokensContained = 0;
    private Role owner;
    ArrayList<Token> tokens = new ArrayList<>(4);


    public Square(boolean isStart, boolean isSpecial, boolean isWinning) {
        this.isStart = isStart;
        this.isSpecial = isSpecial;
        this.isWinning = isWinning;
    }


    public void addToken(Role role, Token token) {
        if (owner != role)
            killTokens();

        owner = role;
        isEmpty = false;
        tokensContained++;

        if (tokensContained > 1) {
            isWall = true;
        }

        tokens.add(token);
    }

    public Token removeToken(int whereTo) {

        Token last = tokens.getLast();
        last.currentIndex = whereTo;
        tokens.removeLast();
        tokensContained--;

        if (tokensContained < 2) {
            isWall = false;
            if (tokensContained == 0) {
                killTokens();
            }
        }

        return last;
    }

    public void killTokens() {
        for (Token token : tokens) {
            token.currentIndex = -1;
        }
        tokens.clear();
        owner = null;
        isEmpty = true;
        isWall = false;
        tokensContained = 0;
    }

//    public void makeEmpty() {
//        isEmpty = true;
//        owner = null;
//        tokensContained = 0;
//    }

    @Override
    public String toString() {

        String output = "";

        if (isEmpty && owner == Role.Player && isWinning)
            return "\uD83D\uDD38";
        if (isEmpty && owner == Role.CPU && isWinning)
            return "\uD83D\uDD39";


        if (owner == Role.Player && isWall)
            return "♦\uFE0F";

        if (owner == Role.CPU && isWall)
            return "\uD83D\uDD37";

        if (owner == Role.Player)
            return "🔴";
        if (owner == Role.CPU)
            return "🔵";

        if (isStart && isEmpty)
            return "\uD83D\uDD04";

        if (isSpecial && isEmpty)
            return "☯\uFE0F";

        if (isEmpty)
            return "⬜\uFE0F";

        return output;
    }


    public void setOwner(Role owner) {
        this.owner = owner;
    }

    public Role getOwner() {
        return owner;
    }

    public Square clone() {
        Square clonedSquare = new Square(this.isStart, this.isSpecial, this.isWinning);

        clonedSquare.isEmpty = this.isEmpty;
        clonedSquare.isWall = this.isWall;
        clonedSquare.tokensContained = this.tokensContained;

        if (this.owner == Role.Player)
            clonedSquare.owner = Role.Player;
        if (this.owner == Role.CPU)
            clonedSquare.owner = Role.CPU;

        clonedSquare.tokens = new ArrayList<>(4);
        for (int i = 0; i < tokens.size(); i++) {
            clonedSquare.tokens.add(tokens.get(i).clone());
        }

        return clonedSquare;
    }


}
