package game;

import java.util.Arrays;

public class Player {
    final Role role;
    final int startIndex;
    final int endIndex;


    int[] tokens = new int[]{-1, -1, -1, -1};


    public Player(Role role, int startIndex, int endIndex) {
        this.role = role;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void sendTo(int before, int after) {
        for (int i = 0; i < 4; i++) {
            if (tokens[i] == before) {
                tokens[i] = after;
                break;
            }
        }
        sortTokens();
    }

    public void sendHome(int value) {
        for (int i = 0; i < 4; i++) {
            if (tokens[i] == value) {
                tokens[i] = -1;
            }
        }
        sortTokens();
    }

    public void sortTokens() {
        if (role == Role.Player) {
            Arrays.sort(tokens);
            for (int i = 0; i < 2; i++) {
                int temp = tokens[i];
                tokens[i] = tokens[3 - i];
                tokens[3 - i] = temp;
            }
        } else {
            for (int i = 0; i < tokens.length; i++) {
                for (int j = 0; j < tokens.length - 1; j++) {

                    int a, b;
                    a = tokens[j];
                    b = tokens[j + 1];


                    if (a > -1 && a < 12)
                        a += 26;
                    if (b > -1 && b < 12)
                        b += 26;

                    if (a < b) {
                        int temp = tokens[j];
                        tokens[j] = tokens[j + 1];
                        tokens[j + 1] = temp;
                    }
                }
            }

        }
    }

    public int getToken(int index) {
        return tokens[index];
    }

    public Role getRole() {
        return role;
    }

    public int[] getTokens() {
        return tokens;
    }

    public int emptyWiningBlocks() {
        int emptyWiningBlocks = 0;
        for (int i = 0; i < 4; i++) {
            if (tokens[i] != 30) {
                emptyWiningBlocks++;
            }
        }
        return emptyWiningBlocks;
    }

    public Player clone() {

        int[] tokensCopy = new int[tokens.length];
        System.arraycopy(tokens, 0, tokensCopy, 0, tokens.length);

        Player cloned = new Player(this.role, this.startIndex, this.endIndex);
        cloned.tokens = tokensCopy;

        return cloned;
    }

}
