package game;

public class Player {
    public Role role;
    int startIndex;
    int endIndex;
    int emptyWiningBlocks = 4;
    Token[] tokens = new Token[4];

    public Player(Role role, int startIndex, int endIndex) {
        this.role = role;
        this.startIndex = startIndex;
        this.endIndex = endIndex;

        for (int i = 0; i < 4; i++) {
            Token token = new Token(role);
            tokens[i] = token;
        }
    }

    public void sortTokens() {
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens.length - 1; j++) {

                int a, b;
                a = tokens[j].currentIndex;
                b = tokens[j + 1].currentIndex;

                if (role == Role.CPU) {
                    if (a > -1 && a < 12)
                        a += 26;
                    if (b > -1 && b < 12)
                        b += 26;
                }

                if (a < b) {
                    Token temp = tokens[j];
                    tokens[j] = tokens[j + 1];
                    tokens[j + 1] = temp;
                }
            }
        }
    }

    public Player clone() {
        Player clone = new Player(role, startIndex, endIndex);

        clone.emptyWiningBlocks = emptyWiningBlocks;

        Token[] clonedTokens = new Token[4];
        for (int i = 0; i < 4; i++) {
            clonedTokens[i] = tokens[i].clone();
        }
        clone.tokens = clonedTokens;
        return clone;
    }

}
