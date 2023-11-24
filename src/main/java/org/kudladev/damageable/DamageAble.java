package org.kudladev.damageable;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class DamageAble {

    protected Rectangle2D object;

    public Rectangle2D getObject() {
        return object;
    }

    public DamageAble(double x, double y, double width, double height){
        this.object = new Rectangle2D(x,y,width,height);
    }


    abstract public void draw(GraphicsContext gc);

}
