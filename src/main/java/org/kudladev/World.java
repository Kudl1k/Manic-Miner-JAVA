package org.kudladev;


import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class World {

    private Point2D gameSize;
    private Point2D infoSize;
    private Player player;


    World(Canvas gameCanvas, Canvas infoCanvas){
        this.gameSize = new Point2D(gameCanvas.getWidth(),gameCanvas.getHeight());
        this.infoSize = new Point2D(infoCanvas.getWidth(),infoCanvas.getHeight());
        this.player = new Player(this);
    }

    void draw(GraphicsContext gc, GraphicsContext ic){
        player.draw(gc);
    }



    // GETTERS
    public Point2D getGameSize() {
        return gameSize;
    }
    public Point2D getInfoSize() {
        return infoSize;
    }
    public Player getPlayer(){
        return this.player;
    }

}
