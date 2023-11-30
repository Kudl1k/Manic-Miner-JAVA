package org.kudladev.utils;

import javafx.scene.image.Image;

public final class Constants {


    private Constants(){}

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;


    public static final int GAME_WINDOW_WIDTH = 800;

    public static final int GAME_WINDOW_HEIGHT = 600;

    public static final int INFO_HEIGHT = 200;
    public static final int GAME_HEIGHT = 400;

    public static final int GRAVITY = 700;
    public static final int JUMPFORCE = 300;

    public static final int FPS = 100;

    public static final Image SPRITESHEET = new Image("/spriteSheet.png");
    public static final Image MAINSCREEN = new Image("/mainmenu.png");


    public static final String tunePath = "/tune.mp3";

    public static final String inGameTunePath = "/in-game-tune.mp3";


    public static final String jumpingPath = "/jumping.mp3";


    public static final String fallingPath = "/falling.mp3";

    public static final String gameOverPath = "/game-over.mp3";

    public static final String diePath = "/die.wav";


}
