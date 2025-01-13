package game;

public class Token {

    Role owner;
    int currentIndex=-1;

    public Token(Role owner) {
        this.owner = owner;
    }


    public Token clone() {
        Token clonedToken = new Token(owner);
        clonedToken.currentIndex=currentIndex;
        return clonedToken;
    }




}
