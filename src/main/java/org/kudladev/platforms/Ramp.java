package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


//Class for referencing Ramp
public class Ramp extends Platform{


    public Ramp(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }
}
