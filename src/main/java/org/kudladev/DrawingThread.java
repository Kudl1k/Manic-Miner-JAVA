package org.kudladev;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingThread extends AnimationTimer {

    private long lastTime = -1;

    private final GraphicsContext gc;

    private final GraphicsContext ic;

    private final Canvas gameCanvas;

    private final Canvas infoCanvas;

    boolean north = false;
    boolean south = false;

    boolean east = false;

    boolean west = false;

    World world;

    public DrawingThread(Canvas gameCanvas, Canvas infoCanvas){
        this.gameCanvas = gameCanvas;
        this.infoCanvas = infoCanvas;
        this.gc = gameCanvas.getGraphicsContext2D();
        this.ic = infoCanvas.getGraphicsContext2D();

        this.world = new World(gameCanvas,infoCanvas);

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> {
                    west = true;
                }
                case D -> {
                    east = true;
                }
                case SPACE -> {
                    if (this.world.getPlayer().onGround()){
                        this.world.getPlayer().jump();
                    }
                }
            }
        });
        gameCanvas.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> {
                    if (this.world.getPlayer().onGround()){
                        if (this.world.getPlayer().checkCollision(Direction.LEFT)){
                            if (this.world.getPlayer().canGoThrough(Direction.LEFT)){
                                this.world.getPlayer().setDir(Direction.NONE);
                                this.world.getPlayer().setVelocity(0,this.world.getPlayer().getVelocity().getY());
                            }
                        }
                    }
                    west = false;

                }
                case D -> {
                    if (this.world.getPlayer().onGround()){
                        if (this.world.getPlayer().checkCollision(Direction.RIGHT)){
                            if (this.world.getPlayer().canGoThrough(Direction.RIGHT)){
                                this.world.getPlayer().setDir(Direction.NONE);
                                this.world.getPlayer().setVelocity(0,this.world.getPlayer().getVelocity().getY());
                            }
                        }
                    }
                    east = false;

                }
            }
        });
        gameCanvas.requestFocus();
    }



    @Override
    public void handle(long now) {
        double deltaT = (now - lastTime) / 1e9;

        if (west) {
            if (this.world.getPlayer().onGround()){
                if (this.world.getPlayer().checkCollision(Direction.LEFT)){

                }
            }
        } else if (east) {
            if (this.world.getPlayer().onGround()){
                if (this.world.getPlayer().checkCollision(Direction.RIGHT)){

                }
            }
        } else {
            if (this.world.getPlayer().onGround() && !this.world.getPlayer().isJumped()) {
                this.world.getPlayer().setVelocity(0, this.world.getPlayer().getVelocity().getY());
            }
        }

        if (deltaT >= 1. / Constants.FPS) {
            gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            ic.clearRect(0, 0, infoCanvas.getWidth(), infoCanvas.getHeight());
            ic.setFill(Color.RED);
            ic.fillRect(0, 0, infoCanvas.getWidth(), infoCanvas.getHeight());
            world.draw(gc, ic);
            if (lastTime > 0) {
                if (this.world.getPlayer().onGround() && !this.world.getPlayer().isJumped() && !west && !east) {
                    this.world.getPlayer().setVelocity(0, this.world.getPlayer().getVelocity().getY());
                }
                world.getPlayer().movement(deltaT);
            }
            lastTime = now;
        }
    }
}
