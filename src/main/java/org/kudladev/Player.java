package org.kudladev;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {

    private Point2D position;
    private Point2D velocity;
    private double speed;
    private boolean jumped;


    private Point2D size;

    private World world;





    Player(World world){
        this.world = world;
        this.size = new Point2D(50,100);
        this.position = new Point2D(0,world.getGameSize().getY()-size.getY());
        this.velocity = new Point2D(0,0);
        this.speed = 100;
        this.jumped = false;
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(),position.getY(),size.getX(),size.getY());
    }

    void jump(){
        if (jumped && getBoundingBox().getMaxY() > 0){
            //TODO: jump mechanic
        }
    }

    public void movement(double deltaT){
        System.out.println(position);
        System.out.println(velocity);
        this.position = this.position.add(velocity.getX() * deltaT,velocity.getY() *  deltaT);
    }

    public void setVelocity(double x, double y){
        this.velocity = new Point2D(x,y);
    }

    public Point2D getVelocity(){
        return velocity;
    }

    public double getSpeed() {
        return speed;
    }

    public Rectangle2D getBoundingBox(){
        return new Rectangle2D(position.getX(),position.getY(),size.getX(),size.getY());
    }

}
