package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameObject implements DrawableObject {

    private Rectangle2D object;

    private boolean canGoThrough;
    private boolean canMoveObjects;
    private Direction directionOfMoving;


    private boolean fallable;

    private double movingSpeed;



    public GameObject(double x, double y, double width,boolean canGoThrough, boolean canMoveObjects, boolean fallable, Direction directionOfMoving) {
        this.object = new Rectangle2D(x,y,width,25);
        this.canGoThrough = canGoThrough;
        this.canMoveObjects = canMoveObjects;
        this.fallable = fallable;
        this.directionOfMoving = directionOfMoving;
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

    public Direction getDirectionOfMoving() {
        return directionOfMoving;
    }

    public void setDirectionOfMoving(Direction directionOfMoving) {
        this.directionOfMoving = directionOfMoving;
    }

    public void setFallable(boolean fallable) {
        this.fallable = fallable;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (canGoThrough){
            gc.setFill(Color.RED);
        } else if (!canGoThrough) {
            gc.setFill(Color.ORANGE);
        }
        if (fallable){
            gc.setFill(Color.YELLOW);
        }
        if (canMoveObjects){
            gc.setFill(Color.LIME);
        }

        gc.fillRect(object.getMinX(),object.getMinY(), object.getWidth(), object.getHeight());
    }

    public void shrinkPlatform(){
        double newHeight = this.object.getHeight()-0.4;
        if (newHeight >= 0){
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),newHeight);
        }else {
            this.object = new Rectangle2D(this.object.getMinX(),this.object.getMinY(),this.object.getWidth(),0);
        }
    }

}
