package org.kudladev;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.damageable.DamageAble;
import org.kudladev.damageable.IceSpikes;
import org.kudladev.damageable.NPC;
import org.kudladev.damageable.Tree;
import org.kudladev.platforms.*;
import org.kudladev.utils.Direction;

import java.io.*;
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

        this.end = new Rectangle2D(gameSize.getX()-45,gameSize.getY()-45,40,40);


        this.player = new Player(this);
    }

    void draw(GraphicsContext gc){
        gc.clearRect(0, 0, gameSize.getX(), gameSize.getY());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameSize.getX(), gameSize.getY());
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
        displayEnd(gc);
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

    private Rectangle2D end;

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
                }
            }
        }
    }

    public void displayEnd(GraphicsContext gc){
        if (this.player.getKeys() == 5){
            gc.setFill(Color.YELLOW);
            gc.fillRect(gameSize.getX()-50,gameSize.getY()-50,50,50);
            gc.setFill(Color.BLUE);
            gc.fillRect(gameSize.getX()-45,gameSize.getY()-45,40,40);

        } else {
            gc.setFill(Color.YELLOW);
            gc.fillRect(gameSize.getX()-50,gameSize.getY()-50,50,50);
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

    public void checkGameState(){
        if (this.player.checkEnd()){
            if (loadScoreFromFile() < getPlayer().getScore()){
                saveScoreToFile(this.player.getScore());
            }
            this.player.restartPlayer();
        } else if (this.player.getKeys() == 5 && this.player.getBoundingBox().intersects(end)) {
            //TODO END
            this.resetModels();
            this.player.restartPlayer();
        }
    }


    public void saveScoreToFile(int score){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/score.txt"))) {
            String scoreStr = Integer.toString(score);
            writer.write(scoreStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadScoreFromFile(){
        int score = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/score.txt"))) {
            String line = reader.readLine();
            if(line != null) {
                score = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

}