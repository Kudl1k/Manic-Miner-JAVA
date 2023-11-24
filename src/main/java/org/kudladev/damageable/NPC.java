package org.kudladev.damageable;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Direction;

public class NPC extends DamageAble{

    private double range;
    private double current = 0;

    private Direction dir = Direction.RIGHT;

    public NPC(double x, double y, double width, double height,double range) {
        super(x, y, width, height);
        this.range = range;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.OLIVE);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
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
