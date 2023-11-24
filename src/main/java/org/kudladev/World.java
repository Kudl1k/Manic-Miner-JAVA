package org.kudladev;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.kudladev.damageable.DamageAble;
import org.kudladev.damageable.IceSpikes;
import org.kudladev.damageable.NPC;
import org.kudladev.damageable.Tree;
import org.kudladev.platforms.*;
import org.kudladev.utils.Direction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {

    private Point2D gameSize;
    private Point2D infoSize;
    private Player player;


    private List<Platform> platforms = new ArrayList<>();
    private List<DamageAble> damageAbles = new ArrayList<>();
    private List<Collectible> collectibles = new ArrayList<>();

    World(Canvas gameCanvas, Canvas infoCanvas){
        this.gameSize = new Point2D(gameCanvas.getWidth(),gameCanvas.getHeight());
        this.infoSize = new Point2D(infoCanvas.getWidth(),infoCanvas.getHeight());

        resetModels();


        this.player = new Player(this);
    }

    void draw(GraphicsContext gc, GraphicsContext ic){
        player.draw(gc);
        for (Platform platform: platforms){
            platform.draw(gc);
        }
        for (DamageAble damageAble: damageAbles){
            damageAble.draw(gc);
        }
        for (Collectible collectible: collectibles){
            collectible.draw(gc);
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

    public List<DamageAble> getDamageAbles() {
        return damageAbles;
    }

    public List<Collectible> getCollectibles() {
        return collectibles;
    }

    public void resetModels(){
        platforms.clear();
        damageAbles.clear();
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

        platforms.add(new MovingBelt(180,gameSize.getY()-155,520, Direction.LEFT));

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

        damageAbles.add(new Tree(275,gameSize.getY()-76,25,25));
        damageAbles.add(new Tree(575, gameSize.getY()-180, 25,25));
        damageAbles.add(new Tree(625, gameSize.getY()-290, 25,25));
        damageAbles.add(new Tree(725, gameSize.getY()-290, 25,25));

        damageAbles.add(new IceSpikes(230, 0, 25,25));
        damageAbles.add(new IceSpikes(400, 0, 25,25));

        damageAbles.add(new NPC(180,gameSize.getY()-195,25,40,245));

        collectibles.add(new Collectible(gameSize.getX()-25,gameSize.getY()-225));
        collectibles.add(new Collectible(180,0));
        collectibles.add(new Collectible(400,25));
        collectibles.add(new Collectible(650, gameSize.getY()-290));
        collectibles.add(new Collectible(gameSize.getX()-50, 0));

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

    public void simulate(double deltaT){
        for (DamageAble damageAble:
             damageAbles) {
            if (damageAble instanceof NPC){
                ((NPC) damageAble).simulate(deltaT);
            }
        }
    }

}