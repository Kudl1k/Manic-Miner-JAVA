package org.kudladev.platforms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import org.kudladev.DrawableObject;

abstract public class Platform implements DrawableObject {

    protected Rectangle2D object;

    public Platform(double x, double y, double width){
        this.object = new Rectangle2D(x,y,width,25);
    }

    public Rectangle2D getObject(){
        return object;
    }

    abstract public void draw(GraphicsContext gc);

}
