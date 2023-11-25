package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.Direction;
import org.kudladev.utils.SpriteSheetValue;

public class MovingBelt extends Platform{

    private Direction directionOfMoving;

    private SpriteSheetValue movingBeltSprite = new SpriteSheetValue(240,130,15,13);


    public MovingBelt(double x, double y, double width, Direction directionOfMoving) {
        super(x, y, width);
        this.directionOfMoving = directionOfMoving;
    }

    public Direction getDirectionOfMoving() {
        return directionOfMoving;
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (double i = 0; i < object.getWidth(); i += 25) {
            gc.drawImage(Constants.SPRITESHEET, movingBeltSprite.sourceX(), movingBeltSprite.sourceY(), movingBeltSprite.sourceWidth(), movingBeltSprite.sourceHeight(), object.getMinX() + i, object.getMinY(), 25, object.getHeight());
        }
    }
}
