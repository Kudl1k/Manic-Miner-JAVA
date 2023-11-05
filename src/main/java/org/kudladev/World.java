package org.kudladev;


import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class World {


    private Point2D gameSize;
    private Point2D infoSize;
    private Player player;

    private GameObject[] gameObjects ={};


    World(Canvas gameCanvas, Canvas infoCanvas){
        this.gameSize = new Point2D(gameCanvas.getWidth(),gameCanvas.getHeight());
        this.infoSize = new Point2D(infoCanvas.getWidth(),infoCanvas.getHeight());
        this.gameObjects = new GameObject[]{
                //GROUND
                new GameObject(
                        0,
                        gameSize.getY(),
                        gameSize.getX()
                ),
                //First platform
                new GameObject(
                        75,
                        gameSize.getY()-75,
                        100
                ),
                //Second platfom
                new GameObject(
                        175,
                        gameSize.getY()-100,
                        100
                )
        };
        this.player = new Player(this);
    }

    void draw(GraphicsContext gc, GraphicsContext ic){
        player.draw(gc);
        for (GameObject gameObject: gameObjects){
            gameObject.draw(gc);
        }
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

    public GameObject[] getGameObjects() {
        return gameObjects;
    }
}
