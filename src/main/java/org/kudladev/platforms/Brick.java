package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;

public class Brick extends Platform {


    private SpriteSheetValue brickSprite = new SpriteSheetValue(225,128,13,15);


    public Brick(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (double i = 0; i < object.getWidth(); i += 25) {
            gc.drawImage(Constants.SPRITESHEET, brickSprite.sourceX(), brickSprite.sourceY(), brickSprite.sourceWidth(), brickSprite.sourceHeight(), object.getMinX() + i, object.getMinY(), 25, object.getHeight());
        }
    }
}
