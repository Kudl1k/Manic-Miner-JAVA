package org.kudladev;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.kudladev.platforms.*;

import java.util.ArrayList;
import java.util.List;

public class World {

    private Point2D gameSize;
    private Point2D infoSize;
    private Player player;

    private List<GameObject> gameObjects = new ArrayList<>(); // Changed to ArrayList

    private List<Platform> platforms = new ArrayList<>();

    World(Canvas gameCanvas, Canvas infoCanvas){
        this.gameSize = new Point2D(gameCanvas.getWidth(),gameCanvas.getHeight());
        this.infoSize = new Point2D(infoCanvas.getWidth(),infoCanvas.getHeight());

        platforms.add(new Ground(0, gameSize.getY(), gameSize.getX()));

        platforms.add(new Ramp(100, gameSize.getY()-51, 400));
        platforms.add(new Ramp(750, gameSize.getY()-76, gameSize.getX()-750));
        platforms.add(new Ramp(755, gameSize.getY()-130, gameSize.getX()-755));

        platforms.add(new Brick(500, gameSize.getY()-76, 100));
        platforms.add(new Brick(450, gameSize.getY()-170, 100));

        platforms.add(new MovingBelt(150,gameSize.getY()-145,580,Direction.LEFT));

        platforms.add(new Trap(600, gameSize.getY()-76, 50));
        platforms.add(new Trap(650, gameSize.getY()-76, 50));
        platforms.add(new Trap(700, gameSize.getY()-76, 50));


        this.player = new Player(this);
    }

    void draw(GraphicsContext gc, GraphicsContext ic){
        player.draw(gc);
        for (Platform platform: platforms){
            platform.draw(gc);
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


    public Platform[] getPlatforms() {
        return platforms.toArray(new Platform[0]);
    }

    public void deleteFeltPlatforms(){
        gameObjects.removeIf(gameObject -> gameObject.getObject().getHeight() == 0);
    }
}