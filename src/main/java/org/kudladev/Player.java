package org.kudladev;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player implements DrawableObject {

    private Point2D position;
    private Point2D velocity;
    private final double speed;
    private boolean jumped;
    private GameObject ground;
    private final Point2D size;

    private final World world;

    Player(World world) {
        this.world = world;
        this.size = new Point2D(25, 50);
        this.position = new Point2D(0, world.getGameSize().getY() - size.getY());
        this.velocity = new Point2D(0, 0);
        this.speed = 100;
        this.jumped = false;
        this.ground = world.getGameObjects()[0];
    }

    //SETTERS
    public void setVelocity(double x, double y) {
        this.velocity = new Point2D(x, y);
    }

    //GETTERS
    public Point2D getVelocity() {
        return velocity;
    }

    public double getSpeed() {
        return speed;
    }


    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }


    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
    }

    void jump() {
        if (!jumped) {
            this.velocity = new Point2D(velocity.getX(), -Constants.JUMPFORCE);
            this.jumped = true;
        }
    }


    public void movement(double deltaT) {
        velocity = velocity.add(0, Constants.GRAVITY * deltaT); // apply gravity
        position = position.add(velocity.multiply(deltaT)); // move player

        assignGround();

        // Correcting position in x-axis
        if (this.position.getX() < 0) {
            this.position = new Point2D(0, this.position.getY());
        } else if (this.position.getX() > this.world.getGameSize().getX() - this.size.getX()) {
            this.position = new Point2D(this.world.getGameSize().getX() - this.size.getX(), this.position.getY());
        }
        if (this.position.getY() < 0) {
            // Fixing position if player is above the top boundary
            this.position = new Point2D(this.position.getX(), 0);
            velocity = new Point2D(velocity.getX(), Constants.GRAVITY); // start falling again
        } else if (this.onGround() && velocity.getY() > 0) {
            System.out.println(velocity);
            // Fixing position if player is below the bottom boundary
            this.position = new Point2D(this.position.getX(), ground.getObject().getMinY() - this.size.getY());
            jumped = false;
            velocity = new Point2D(velocity.getX(), 0); // stop downward movement
        }
    }

    public boolean checkCollision(Direction dir) {
        switch (dir) {
            case RIGHT -> {
                if (getBoundingBox().getMaxX() < world.getGameSize().getX()) {
                    return true;
                } else {
                    this.velocity = new Point2D(this.velocity.getX(), 0);
                    return false;
                }
            }
            case LEFT -> {
                if (getBoundingBox().getMinX() >= 0) {
                    return true;
                } else {
                    this.velocity = new Point2D(this.velocity.getX(), 0);
                    return false;
                }
            }
        }
        return true;
    }


    public boolean onGround() {
        return getBoundingBox().getMaxY() >= ground.getObject().getMinY();
    }


    public void assignGround() {
        for (GameObject gameObject :
                world.getGameObjects()) {
            if (getBoundingBox().getMaxX() < gameObject.getObject().getMaxX() + size.getX() &&
                    getBoundingBox().getMinX() > gameObject.getObject().getMinX() - size.getX() &&
                    getBoundingBox().getMaxY() - (size.getX() / 2) <= gameObject.getObject().getMinY()
            ) {
                ground = gameObject;
            }
        }
    }

    public boolean isJumped() {
        return jumped;
    }
}









