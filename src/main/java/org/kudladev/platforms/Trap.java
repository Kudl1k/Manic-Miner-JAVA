package org.kudladev.platforms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Trap extends Platform{

    public Trap(double x, double y, double width) {
        super(x, y, width);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }

    public void shrinkPlatform(){
        double newHeight = this.object.getHeight()-0.5;
        if (newHeight >= 0){
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),newHeight);
        }else {
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),0);
        }
    }

}
