package org.kudladev;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.Direction;

public class DrawingThread extends AnimationTimer {

    private long lastTime = -1;

    private final GraphicsContext gc;

    private final GraphicsContext ic;

    private final GraphicsContext lc;

    private final Canvas gameCanvas;

    private final Canvas infoCanvas;

    private final Canvas lowerCanvas;

    boolean north = false;
    boolean south = false;

    boolean east = false;

    boolean west = false;

    private World world;
    private Info info;

    public DrawingThread(Canvas gameCanvas, Canvas infoCanvas,Canvas lowerCanvas) {
        this.gameCanvas = gameCanvas;
        this.infoCanvas = infoCanvas;
        this.lowerCanvas = lowerCanvas;

        this.gc = gameCanvas.getGraphicsContext2D();
        this.ic = infoCanvas.getGraphicsContext2D();
        this.lc = lowerCanvas.getGraphicsContext2D();

        this.world = new World(gameCanvas,infoCanvas);
        this.info = new Info(infoCanvas,this.world);


        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> {
                    if (!east){
                        west = true;
                    }
                }
                case D -> {
                    if (!west){
                        east = true;
                    }
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
                    west = false;
                    if (this.world.getPlayer().onGround()){
                        this.world.getPlayer().checkCollision(Direction.LEFT);
                    }
                }
                case D -> {
                    east = false;
                    if (this.world.getPlayer().onGround()){
                        this.world.getPlayer().checkCollision(Direction.RIGHT);
                    }
                }
            }
        });
        gameCanvas.requestFocus();
    }



    @Override
    public void handle(long now) {
        double deltaT = (now - lastTime) / 1e9;
        this.world.deleteFeltPlatforms();
        this.world.getPlayer().correctPosition(west,east);
        this.world.getPlayer().checkDamage();
        this.world.getPlayer().checkCollectibles();
        this.world.getPlayer().air(deltaT);
        this.world.checkGameState();
        if (deltaT >= 1. / Constants.FPS) {
            lc.clearRect(0,0, lowerCanvas.getWidth(),lowerCanvas.getHeight());
            lc.setFill(Color.PURPLE);
            lc.fillRect(0,0,lowerCanvas.getWidth(),lowerCanvas.getHeight());
            world.draw(gc);
            info.draw(ic);
            if (lastTime > 0) {
                world.getPlayer().movement(deltaT);
                world.simulate(deltaT);
            }
            lastTime = now;
        }
    }
}
