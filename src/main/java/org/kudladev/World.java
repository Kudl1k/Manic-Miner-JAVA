package org.kudladev;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class World {

    private Point2D gameSize;
    private Point2D infoSize;
    private Player player;

    private List<GameObject> gameObjects = new ArrayList<>(); // Changed to ArrayList

    World(Canvas gameCanvas, Canvas infoCanvas){
        this.gameSize = new Point2D(gameCanvas.getWidth(),gameCanvas.getHeight());
        this.infoSize = new Point2D(infoCanvas.getWidth(),infoCanvas.getHeight());

        // Modifying list insertion
        gameObjects.add(new GameObject(0, gameSize.getY(), gameSize.getX(), false, false, false));
        gameObjects.add(new GameObject(100, gameSize.getY()-51, 400, true, false, false));
        gameObjects.add(new GameObject(500, gameSize.getY()-76, 100, false, false, false));
        gameObjects.add(new GameObject(450, gameSize.getY()-160, 100, false, false, false));
        gameObjects.add(new GameObject(600, gameSize.getY()-76, 25, true, false, true));
        gameObjects.add(new GameObject(625, gameSize.getY()-76, 25, true, false, true));
        gameObjects.add(new GameObject(650, gameSize.getY()-76, 25, true, false, true));
        gameObjects.add(new GameObject(675, gameSize.getY()-76, gameSize.getX()-675, true, false, false));

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

    // Return as array
    public GameObject[] getGameObjects() {
        return gameObjects.toArray(new GameObject[0]);
    }

    // Removing using removeIf method of ArrayList
    public void deleteFeltPlatforms(){
        gameObjects.removeIf(gameObject -> gameObject.getObject().getHeight() == 0);
    }
}