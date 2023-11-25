package org.kudladev.damageable;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.Direction;
import org.kudladev.utils.SpriteSheetList;
import org.kudladev.utils.SpriteSheetValue;

public class NPC extends DamageAble{

    private double range;
    private double current = 0;

    private Direction dir = Direction.RIGHT;

    private SpriteSheetList npcSprites = new SpriteSheetList();


    public NPC(double x, double y, double width, double height,double range) {
        super(x, y, width, height);
        this.range = range;


        npcSprites.add(new SpriteSheetValue(128,0,10,16));
        npcSprites.add(new SpriteSheetValue(146,0, 10,16));
        npcSprites.add(new SpriteSheetValue(164,0,10,16));
        npcSprites.add(new SpriteSheetValue(182,0,10,16));
        npcSprites.add(new SpriteSheetValue(245, 0, 10,16));
        npcSprites.add(new SpriteSheetValue(227,0,10,16));
        npcSprites.add(new SpriteSheetValue(209,0,10,16));
        npcSprites.add(new SpriteSheetValue(191,0,10,16));
    }

    @Override
    public void draw(GraphicsContext gc) {
        switch (dir) {
            case RIGHT -> {
                switch ((int) ((this.object.getMinX() % 40) / 10)) {
                    case 0 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(0).sourceX(), npcSprites.getSprites().get(0).sourceY(), npcSprites.getSprites().get(0).sourceWidth(), npcSprites.getSprites().get(0).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                    case 1 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(1).sourceX(), npcSprites.getSprites().get(1).sourceY(), npcSprites.getSprites().get(1).sourceWidth(), npcSprites.getSprites().get(1).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                    case 2 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(2).sourceX(), npcSprites.getSprites().get(2).sourceY(), npcSprites.getSprites().get(2).sourceWidth(), npcSprites.getSprites().get(2).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                    case 3 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(3).sourceX(), npcSprites.getSprites().get(3).sourceY(), npcSprites.getSprites().get(3).sourceWidth(), npcSprites.getSprites().get(3).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                }
            }
            case LEFT -> {
                switch ((int) ((this.object.getMinX() % 40) / 10)) {
                    case 0 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(4).sourceX(), npcSprites.getSprites().get(4).sourceY(), npcSprites.getSprites().get(4).sourceWidth(), npcSprites.getSprites().get(4).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                    case 1 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(5).sourceX(), npcSprites.getSprites().get(5).sourceY(), npcSprites.getSprites().get(5).sourceWidth(), npcSprites.getSprites().get(5).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                    case 2 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(6).sourceX(), npcSprites.getSprites().get(6).sourceY(), npcSprites.getSprites().get(6).sourceWidth(), npcSprites.getSprites().get(6).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                    case 3 -> {
                        gc.drawImage(Constants.SPRITESHEET, npcSprites.getSprites().get(7).sourceX(), npcSprites.getSprites().get(7).sourceY(), npcSprites.getSprites().get(7).sourceWidth(), npcSprites.getSprites().get(7).sourceHeight(), this.object.getMinX(), this.object.getMinY(), this.object.getWidth(), this.object.getHeight());
                    }
                }
            }
        }
    }


    public void simulate(double deltaT){
        switch (dir){
            case RIGHT -> {
                double addedValue = 50 * deltaT;
                current += addedValue;
                if (current < range){
                    addXValue(addedValue);
                } else {
                    this.dir = Direction.LEFT;
                }
            }
            case LEFT -> {
                double addedValue = -50 * deltaT;
                current += addedValue;
                if (current > 0){
                    addXValue(addedValue);
                } else {
                    this.dir = Direction.RIGHT;
                }
            }
        }
    }

    public void addXValue(double value){
        this.object = new Rectangle2D(this.object.getMinX() + value,this.object.getMinY(),this.object.getWidth(),this.object.getHeight());
    }

}
