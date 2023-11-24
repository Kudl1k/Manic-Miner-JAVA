package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Collectible {

    private Rectangle2D object;


    public Collectible(double x, double y){
        this.object = new Rectangle2D(x,y,25,25);
    }

    public Rectangle2D getObject() {
        return object;
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.GREY);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }

}
