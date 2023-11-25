package org.kudladev.platforms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;


//Class for referencing the ground
public class Ground extends Platform {


    public Ground(double x, double y, double width) {
        super(x, y, width);
    }

    private SpriteSheetValue groundSprite = new SpriteSheetValue(192,128,15,15);


    @Override
    public void draw(GraphicsContext gc) {
        double numberOfBlocks = (object.getWidth()/15);

        for (int i = 0; i < numberOfBlocks; i++) {
            gc.drawImage(Constants.SPRITESHEET, groundSprite.sourceX(), groundSprite.sourceY(), groundSprite.sourceWidth(), groundSprite.sourceHeight(), object.getMinX() + i*15, object.getMinY(), numberOfBlocks, object.getHeight());
        }

//        gc.setFill(Color.RED);
//        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }
}
