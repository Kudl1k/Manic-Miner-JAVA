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
        //GROUND
        gameObjects.add(new GameObject(0, gameSize.getY(), gameSize.getX(), false, false, false,Direction.NONE));
        //PLATFORMS
        gameObjects.add(new GameObject(100, gameSize.getY()-51, 400, true, false, false,Direction.NONE));
        gameObjects.add(new GameObject(750, gameSize.getY()-76, gameSize.getX()-750, true, false, false,Direction.NONE));
        gameObjects.add(new GameObject(755, gameSize.getY()-130, gameSize.getX()-755, true, false, false,Direction.NONE));
        //BRICK PLATFORM
        gameObjects.add(new GameObject(500, gameSize.getY()-76, 100, false, false, false,Direction.NONE));
        gameObjects.add(new GameObject(450, gameSize.getY()-170, 100, false, false, false,Direction.NONE));
        //MOVEABLE PLATFORMS
        gameObjects.add(new GameObject(150,gameSize.getY()-145,580,true,false,false,Direction.LEFT));
        //FALLABLES
        gameObjects.add(new GameObject(600, gameSize.getY()-76, 50, true, false, true,Direction.NONE));
        gameObjects.add(new GameObject(650, gameSize.getY()-76, 50, true, false, true,Direction.NONE));
        gameObjects.add(new GameObject(700, gameSize.getY()-76, 50, true, false, true,Direction.NONE));


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