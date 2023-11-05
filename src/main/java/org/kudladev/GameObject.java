package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameObject implements DrawableObject {

    private Rectangle2D object;


    public GameObject(double x, double y, double width) {
        this.object = new Rectangle2D(x,y,width,25);
    }

    public Rectangle2D getObject() {
        return object;
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }
}
