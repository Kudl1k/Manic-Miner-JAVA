package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;

public class Collectible {

    private Rectangle2D object;

    private SpriteSheetValue collectibleSprite = new SpriteSheetValue(161,130,11,14);




    public Collectible(double x, double y){
        this.object = new Rectangle2D(x,y,25,25);
    }

    public Rectangle2D getObject() {
        return object;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(Constants.SPRITESHEET, collectibleSprite.sourceX(), collectibleSprite.sourceY(), collectibleSprite.sourceWidth(), collectibleSprite.sourceHeight(), object.getMinX(), object.getMinY(), object.getWidth(), object.getHeight());

    }

}
