package org.kudladev;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.kudladev.utils.*;

public class DrawingThread extends AnimationTimer {

    private long lastTime = -1;

    private final GraphicsContext gc;

    private final GraphicsContext ic;

    private final GraphicsContext lc;

    private final Canvas gameCanvas;

    private final Canvas infoCanvas;

    private final Canvas lowerCanvas;

    private final Screens screens;

    boolean north = false;
    boolean south = false;

    boolean east = false;

    boolean west = false;

    private World world;
    private Info info;

    private Menu menuState;

    private Music music;

    public DrawingThread(Canvas gameCanvas, Canvas infoCanvas,Canvas lowerCanvas) {
        this.gameCanvas = gameCanvas;
        this.infoCanvas = infoCanvas;
        this.lowerCanvas = lowerCanvas;

        this.gc = gameCanvas.getGraphicsContext2D();
        this.ic = infoCanvas.getGraphicsContext2D();
        this.lc = lowerCanvas.getGraphicsContext2D();

        this.music = new Music();
        this.menuState = Menu.LOADING;


        this.world = new World(gameCanvas,infoCanvas,this.music);
        this.info = new Info(infoCanvas,this.world);
        this.screens = new Screens(this.world);





        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> {
                    if (!east && this.world.getPlayer().getState() == GameState.RUNNING){
                        west = true;
                    }
                }
                case D -> {
                    if (!west && this.world.getPlayer().getState() == GameState.RUNNING){
                        east = true;
                    }
                }
                case SPACE -> {
                    if (this.world.getPlayer().onGround() && this.world.getPlayer().getState() == GameState.RUNNING){
                        this.world.getPlayer().jump();
                        music.playJumping();
                    }
                }
                case ENTER -> {
                    if (this.menuState == Menu.LOADING){
                        this.menuState = Menu.MAIN;
                    } else if (this.menuState == Menu.MAIN) {
                        this.world.getPlayer().setState(GameState.RUNNING);
                        west = false;
                        east = false;
                        this.world.getPlayer().restartPlayer();
                        this.menuState = Menu.GAME;
                    } else if (this.menuState == Menu.WIN || this.menuState == Menu.LOSE) {
                        this.menuState = Menu.MAIN;
                    }
                }
            }
        });
        gameCanvas.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> {
                    if (this.world.getPlayer().getState() == GameState.RUNNING){
                        west = false;
                        if (this.world.getPlayer().onGround()){
                            this.world.getPlayer().checkCollision(Direction.LEFT);
                        }
                    }

                }
                case D -> {
                    if (this.world.getPlayer().getState() == GameState.RUNNING) {
                        east = false;
                        if (this.world.getPlayer().onGround()) {
                            this.world.getPlayer().checkCollision(Direction.RIGHT);
                        }
                    }
                }
            }
        });
        gameCanvas.requestFocus();
    }



    @Override
    public void handle(long now) {

        double deltaT = (now - lastTime) / 1e9;
        switch (menuState){
            case LOADING -> {
                music.playTune();
                if (deltaT >= 1. / Constants.FPS) {
                    this.screens.loadingScreen(lc);
                    if (lastTime > 0) {
                        screens.clock(deltaT);
                    }
                    lastTime = now;
                }
            }
            case MAIN -> {
                music.getTunePlayer().stop();
                this.screens.mainScreen(lc);
            }
            case GAME -> {
                if (this.world.getPlayer().getState() == GameState.RUNNING){
                    this.world.deleteFeltPlatforms();
                    this.world.getPlayer().correctPosition(west,east);
                    this.world.getPlayer().checkDamage();
                    this.world.getPlayer().checkCollectibles();
                    this.world.getPlayer().air(deltaT);
                    this.world.checkGameState();
                }
                if (deltaT >= 1. / Constants.FPS) {
                    world.draw(gc);
                    info.draw(ic);
                    if (lastTime > 0) {
                        if (this.world.getPlayer().getState() == GameState.RUNNING){
                            world.getPlayer().movement(deltaT);
                            world.simulate(deltaT);
                        } else {
                            world.getPlayer().endGame(deltaT);
                        }
                    }
                    lastTime = now;
                }
                if (this.world.getPlayer().getState() == GameState.RUNNING){
                    this.music.playInGameTune();
                } else {
                    this.music.getFallingPlayer().stop();
                    this.music.getJumpingPlayer().stop();
                    this.music.getInGameTunePlayer().stop();
                    if (this.world.getPlayer().getState() == GameState.WIN){
                        this.menuState = Menu.WIN;
                        clearUpperCanvases();
                    } else if (this.world.getPlayer().getState() == GameState.LOSE) {
                        this.menuState = Menu.LOSE;
                        clearUpperCanvases();
                    }
                }
            }
            case LOSE,WIN -> {
                screens.endScreen(lc);
            }

        }
    }

    private void clearUpperCanvases(){
        gc.clearRect(0,0,Constants.GAME_WINDOW_WIDTH,Constants.GAME_HEIGHT);
        ic.clearRect(0,0,Constants.GAME_WINDOW_WIDTH,Constants.INFO_HEIGHT);
    }
}

