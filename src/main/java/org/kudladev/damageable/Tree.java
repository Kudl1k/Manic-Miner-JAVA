package org.kudladev.damageable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tree extends DamageAble{

    public Tree(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }
}
