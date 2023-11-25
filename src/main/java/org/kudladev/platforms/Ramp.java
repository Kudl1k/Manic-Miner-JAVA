package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;


//Class for referencing Ramp
public class Ramp extends Platform{


    private SpriteSheetValue rampSprite = new SpriteSheetValue(193,128,13,15);


    public Ramp(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (double i = 0; i < object.getWidth(); i += 25) {
            gc.drawImage(Constants.SPRITESHEET, rampSprite.sourceX(), rampSprite.sourceY(), rampSprite.sourceWidth(), rampSprite.sourceHeight(), object.getMinX() + i, object.getMinY(), 25, object.getHeight());
        }


    }
}
