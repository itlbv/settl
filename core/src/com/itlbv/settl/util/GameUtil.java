package com.itlbv.settl.util;

public class GameUtil {

    public static int gameSpeed = 1;
    private static int previousGameSpeed = 1;

    public static void increaseGameSpeed() {
        switch (gameSpeed) {
            case 0: gameSpeed = 3; break;
            case 2: gameSpeed = 1; break;
            case 3: gameSpeed = 2; break;
        }
    }

    public static void decreaseGameSpeed() {
        switch (gameSpeed) {
            case 1: gameSpeed = 2; break;
            case 2: gameSpeed = 3; break;
            case 3: gameSpeed = 0; break;
        }
    }

    public static void changePauseState() {
        if (gameSpeed == 0) {
            gameSpeed = previousGameSpeed;
        } else {
            previousGameSpeed = gameSpeed;
            gameSpeed = 0;
        }
    }
}
