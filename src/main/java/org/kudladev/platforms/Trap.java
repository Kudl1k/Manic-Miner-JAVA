package org.kudladev.platforms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;

public class Trap extends Platform{

    private SpriteSheetValue trapSprite = new SpriteSheetValue(209,128,13,15);

    private double height = 25;

    public Trap(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (double i = 0; i < object.getWidth(); i += 25) {
            gc.drawImage(Constants.SPRITESHEET, trapSprite.sourceX(), trapSprite.sourceY() , trapSprite.sourceWidth(), trapSprite.sourceHeight() + (25-height), object.getMinX() + i, object.getMinY() + (25-height), 25, 25);
        }
    }

    public void shrinkPlatform(){
        double newHeight = this.object.getHeight()-0.7;
        height = newHeight;
        if (newHeight >= 0){
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),newHeight);
        }else {
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),0);
        }
    }

}
