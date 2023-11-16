package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameObject implements DrawableObject {

    private Rectangle2D object;

    private boolean canGoThrough;
    private boolean canMoveObjects;

    private boolean fallable;

    private double movingSpeed;



    public GameObject(double x, double y, double width,boolean canGoThrough, boolean canMoveObjects, boolean fallable) {
        this.object = new Rectangle2D(x,y,width,25);
        this.canGoThrough = canGoThrough;
        this.canMoveObjects = canMoveObjects;
        this.fallable = fallable;
    }

    public Rectangle2D getObject() {
        return object;
    }

    //SETTERS & GETTERS


    public void setObject(Rectangle2D object) {
        this.object = object;
    }

    public boolean isCanGoThrough() {
        return canGoThrough;
    }

    public boolean isFallable() {
        return fallable;
    }

    public void setCanGoThrough(boolean canGoThrough) {
        this.canGoThrough = canGoThrough;
    }

    public boolean isCanMoveObjects() {
        return canMoveObjects;
    }


    public void setCanMoveObjects(boolean canMoveObjects) {
        this.canMoveObjects = canMoveObjects;
    }

    public double getMovingSpeed() {
        return movingSpeed;
    }

    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }

    public void shrinkPlatform(){
        double newHeight = this.object.getHeight()-1;
        if (newHeight >= 0){
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY()+1,this.object.getWidth(),newHeight);
        }else {
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),0);
        }
    }

}
