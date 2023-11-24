package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Platform {


    public Brick(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }
}
