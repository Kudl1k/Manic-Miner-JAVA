package org.kudladev.damageable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetValue;

public class IceSpikes extends DamageAble{


    private SpriteSheetValue iceSpikeSprite = new SpriteSheetValue(144,130,15,12);


    public IceSpikes(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(Constants.SPRITESHEET, iceSpikeSprite.sourceX(), iceSpikeSprite.sourceY(), iceSpikeSprite.sourceWidth(), iceSpikeSprite.sourceHeight(), object.getMinX(), object.getMinY(), object.getWidth(), object.getHeight());


    }
}
