package org.kudladev.platforms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;

public class Trap extends Platform{

    private SpriteSheetValue trapSprite = new SpriteSheetValue(209,128,13,15);


    public Trap(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (double i = 0; i < object.getWidth(); i += 25) {
            gc.drawImage(Constants.SPRITESHEET, trapSprite.sourceX(), trapSprite.sourceY(), trapSprite.sourceWidth(), trapSprite.sourceHeight(), object.getMinX() + i, object.getMinY(), 25, object.getHeight());
        }
    }

    public void shrinkPlatform(){
        double newHeight = this.object.getHeight()-0.7;
        if (newHeight >= 0){
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),newHeight);
        }else {
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),0);
        }
    }

}
