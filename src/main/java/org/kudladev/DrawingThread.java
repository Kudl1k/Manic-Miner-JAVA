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
                    this.world.getPlayer().setVelocity(-this.world.getPlayer().getSpeed(),this.world.getPlayer().getVelocity().getY());
                }
                case D -> {
                    east = true;
                    this.world.getPlayer().setVelocity(this.world.getPlayer().getSpeed(),this.world.getPlayer().getVelocity().getY());

                }
                case SPACE -> {
                    //TODO:
                }
            }
        });
        gameCanvas.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()){
                case A -> {
                    west = false;
                    this.world.getPlayer().setVelocity(0,this.world.getPlayer().getVelocity().getY());
                }
                case D -> {
                    east = false;
                    this.world.getPlayer().setVelocity(0,this.world.getPlayer().getVelocity().getY());

                }
            }
            System.out.println(this.world.getPlayer().getVelocity());
        });
        gameCanvas.requestFocus();
    }



    @Override
    public void handle(long now) {


        double deltaT = (now - lastTime) / 1e9;
        if (deltaT >= 1./Constants.FPS) {
            gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            gc.setFill(Color.BLACK);
            gc.fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
            ic.clearRect(0,0, infoCanvas.getWidth(),infoCanvas.getHeight());
            ic.setFill(Color.RED);
            ic.fillRect(0,0,infoCanvas.getWidth(),infoCanvas.getHeight());
            world.draw(gc,ic);
            if (lastTime > 0) {

                world.getPlayer().movement(deltaT);

            }
            lastTime = now;
        }

    }
}
