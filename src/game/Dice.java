package game;

import java.util.Random;

public class Dice {


    Random random = new Random();

    public int rollDice(){
        return random.nextInt(6) + 1;
    }


}
