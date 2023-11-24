package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Direction;

public class MovingBelt extends Platform{

    private Direction directionOfMoving;

    public MovingBelt(double x, double y, double width, Direction directionOfMoving) {
        super(x, y, width);
        this.directionOfMoving = directionOfMoving;
    }

    public Direction getDirectionOfMoving() {
        return directionOfMoving;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIME);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }
}
