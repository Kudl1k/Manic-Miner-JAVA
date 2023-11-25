package org.kudladev.damageable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetList;
import org.kudladev.utils.SpriteSheetValue;

public class Tree extends DamageAble{

    private SpriteSheetValue treeSprite = new SpriteSheetValue(177,131,11,11);


    public Tree(double x, double y, double width, double height) {
        super(x, y, width, height);



    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(Constants.SPRITESHEET, treeSprite.sourceX(), treeSprite.sourceY(), treeSprite.sourceWidth(), treeSprite.sourceHeight(), object.getMinX(), object.getMinY(), object.getWidth(), object.getHeight());
    }
}
