package org.kudladev;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.kudladev.platforms.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {

    private Point2D gameSize;
    private Point2D infoSize;
    private Player player;


    private List<Platform> platforms = new ArrayList<>();

    World(Canvas gameCanvas, Canvas infoCanvas){
        this.gameSize = new Point2D(gameCanvas.getWidth(),gameCanvas.getHeight());
        this.infoSize = new Point2D(infoCanvas.getWidth(),infoCanvas.getHeight());

        platforms.add(new Ground(0, gameSize.getY(), gameSize.getX()));

        platforms.add(new Ramp(100, gameSize.getY()-51, 400));
        platforms.add(new Ramp(725, gameSize.getY()-76, gameSize.getX()-725));
        platforms.add(new Ramp(755, gameSize.getY()-130, gameSize.getX()-755));
        platforms.add(new Ramp(0, gameSize.getY()-155, 100));
        platforms.add(new Ramp(0, gameSize.getY()-210, 75));
        platforms.add(new Ramp(0, gameSize.getY()-265, 375));
        platforms.add(new Ramp(475, gameSize.getY()-265, 25));
        platforms.add(new Ramp(600,gameSize.getY()-265,gameSize.getX()-600));



        platforms.add(new Brick(500, gameSize.getY()-76, 100));
        platforms.add(new Brick(450, gameSize.getY()-180, 100));

        platforms.add(new MovingBelt(180,gameSize.getY()-155,520,Direction.LEFT));

        platforms.add(new Trap(600, gameSize.getY()-76, 25));
        platforms.add(new Trap(625, gameSize.getY()-76, 25));
        platforms.add(new Trap(650, gameSize.getY()-76, 25));
        platforms.add(new Trap(675, gameSize.getY()-76, 25));
        platforms.add(new Trap(700, gameSize.getY()-76, 25));

        platforms.add(new Trap(375, gameSize.getY()-265, 25));
        platforms.add(new Trap(400, gameSize.getY()-265, 25));
        platforms.add(new Trap(425, gameSize.getY()-265, 25));
        platforms.add(new Trap(450, gameSize.getY()-265, 25));

        platforms.add(new Trap(500, gameSize.getY()-265, 25));
        platforms.add(new Trap(525, gameSize.getY()-265, 25));
        platforms.add(new Trap(550, gameSize.getY()-265, 25));
        platforms.add(new Trap(575, gameSize.getY()-265, 25));


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


    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void deleteFeltPlatforms(){
        Iterator<Platform> platformIterator = this.platforms.iterator();
        while(platformIterator.hasNext()) {
            Platform platform = platformIterator.next();
            if(platform instanceof Trap) {
                if (platform.getObject().getHeight() == 0){
                    this.player.setGround(getPlatforms().get(0));
                    platformIterator.remove();
                    System.out.println("deleted");
                }
            }
        }
    }
}