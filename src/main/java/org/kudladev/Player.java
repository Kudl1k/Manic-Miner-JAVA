package org.kudladev;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.kudladev.platforms.Brick;
import org.kudladev.platforms.MovingBelt;
import org.kudladev.platforms.Platform;
import org.kudladev.platforms.Trap;

public class Player implements DrawableObject {

    private Point2D position;
    private Point2D velocity;
    private final double speed;
    private boolean jumped;
    private Platform ground;
    private final Point2D size;

    private final World world;

    private boolean fall = false;

    private Direction dir = Direction.NONE;

    Player(World world) {
        this.world = world;
        this.size = new Point2D(25, 50);
        this.position = new Point2D(0, world.getGameSize().getY() - size.getY());
        this.velocity = new Point2D(0, 0);
        this.speed = 100;
        this.jumped = false;
        this.ground = world.getPlatforms().get(0);
    }

    //SETTERS
    public void setVelocity(double x, double y) {
        this.velocity = new Point2D(x, y);
    }

    //GETTERS
    public Point2D getVelocity() {
        return velocity;
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
        velocity = velocity.add(0, Constants.GRAVITY * deltaT);
        position = position.add(velocity.multiply(deltaT));
        if (this.position.getX() < 0) {
            this.position = new Point2D(0, this.position.getY());
        } else if (this.position.getX() > this.world.getGameSize().getX() - this.size.getX()) {
            this.position = new Point2D(this.world.getGameSize().getX() - this.size.getX(), this.position.getY());
        }
        if (this.position.getY() < 0) {
            this.position = new Point2D(this.position.getX(), 0);
            velocity = new Point2D(velocity.getX(), Constants.GRAVITY);
        } else if (this.onGround() && velocity.getY() > 0) {

            this.position = new Point2D(this.position.getX(), ground.getObject().getMinY() - this.size.getY());
            jumped = false;
            velocity = new Point2D(velocity.getX(), 0);
        } else if (this.jumped) {
            for (int i = 0; i < this.world.getPlatforms().size(); i++) {
                if (i == 0) continue;
                if (getBoundingBox().intersects(this.world.getPlatforms().get(i).getObject()) && (this.world.getPlatforms().get(i) instanceof Brick)){
                    this.velocity = new Point2D(0,100);
                }
            }
        }
        fall(deltaT);
    }


    public void correctPosition(boolean west, boolean east){
        if (west) {
            if (onGround()){
                checkCollision(Direction.LEFT);
            }
        } else if (east) {
            if (onGround()){
                checkCollision(Direction.RIGHT);
            }
        }else if (onGround() && !isJumped()) {
            velocity = new Point2D(0, velocity.getY());
        }
        checkMovingGround(west,east);
        if (onGround()){
            for (int i = 0; i < this.world.getPlatforms().size(); i++) {
                if (this.world.getPlatforms().get(i) instanceof Trap){
                    if (intersectWithTrap((Trap) this.world.getPlatforms().get(i))){
                        ((Trap) this.world.getPlatforms().get(i)).shrinkPlatform();
                    }
                }
            }
        }
        assignGround();
        checkFall();
    }

    private boolean intersectWithTrap(Trap platform) {
        var boundingBox = getBoundingBox();
        var platformObject = platform.getObject();

        double minYPlus12 = boundingBox.getMaxY() + 0.1;


        boolean intersects = boundingBox.getMaxX() > platformObject.getMinX()
                && boundingBox.getMinX() < platformObject.getMaxX()
                && minYPlus12 > platformObject.getMinY()
                && minYPlus12 < platformObject.getMaxY();

        if (intersects) {
            System.out.println(intersects);
            System.out.println(minYPlus12);
            System.out.println(platformObject.getMinY());
            System.out.println(platformObject.getMaxY());
            return true;
        }

        return false;
    }


    public void checkCollision(Direction dir) {
        switch (dir) {
            case RIGHT -> {
                if (getBoundingBox().getMaxX() < world.getGameSize().getX()) {
                    if (this.world.getPlayer().canGoThrough(Direction.RIGHT)) {
                        this.world.getPlayer().setVelocity(speed, velocity.getY());
                        this.world.getPlayer().setDir(Direction.RIGHT);
                    }
                }
            }
            case LEFT -> {
                if (getBoundingBox().getMinX() >= 0) {
                    if (this.world.getPlayer().canGoThrough(Direction.LEFT)){
                        this.world.getPlayer().setVelocity(-speed,this.velocity.getY());
                        this.world.getPlayer().setDir(Direction.LEFT);
                    }
                }
            }
        }
    }


    public boolean onGround() {
        return getBoundingBox().getMaxY() >= ground.getObject().getMinY() && ground.getObject().getHeight() != 0;
    }

    public Platform getGround() {
        return ground;
    }

    public void setGround(Platform platform){
        ground = platform;
    }

    public void assignGround() {
        for (Platform platform :
                world.getPlatforms()) {
            if (platform.getObject().getHeight() == 0){
                continue;
            }
            if (getBoundingBox().getMaxX() < platform.getObject().getMaxX() + size.getX() &&
                    getBoundingBox().getMinX() > platform.getObject().getMinX() - size.getX() &&
                    getBoundingBox().getMaxY() - (size.getX() / 2) <= platform.getObject().getMinY()
            ) {
                if (ground.getObject().getMinY() > platform.getObject().getMinY() ||
                        (getBoundingBox().getMaxX() < ground.getObject().getMinX() ||
                                getBoundingBox().getMinX() > ground.getObject().getMaxX()))
                {
                    ground = platform;
                }
            }
        }
    }


    public void checkFall() {
        if (!onGround() && !jumped) {
            assignGround();
            this.velocity = new Point2D(0.0,100.0);
            fall = true;
        } else {
            fall = false;
        }
    }

    public void fall(double deltaT){
        if (fall){
            if (getBoundingBox().getMinY() < ground.getObject().getMinY()){
                this.velocity = velocity.add(new Point2D(0.0, Constants.GRAVITY*deltaT));
            } else {
                fall = false;
            }
        }
    }

    public boolean isJumped() {
        return jumped;
    }

    public boolean canGoThrough(Direction dir) {
        for (int i = 0; i < this.world.getPlatforms().size(); i++) {
            if (i == 0) continue;
            if (isInSameHeight(this.world.getPlatforms().get(i))){
                if (this.world.getPlatforms().get(i) instanceof Brick) {
                    if (getBoundingBox().intersects(this.world.getPlatforms().get(i).getObject())){
                        switch (dir){
                            case LEFT -> {
                                this.position = new Point2D(this.world.getPlatforms().get(i).getObject().getMaxX(),this.position.getY());
                            }
                            case RIGHT -> {
                                this.position = new Point2D(this.world.getPlatforms().get(i).getObject().getMinX()-size.getX(),this.position.getY());
                            }
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void checkMovingGround(boolean west, boolean east){
        if (!onGround()){
            return;
        }
        if (ground instanceof MovingBelt){
            switch (((MovingBelt) ground).getDirectionOfMoving()){
                case RIGHT -> {

                    if (west){
                        checkCollision(Direction.LEFT);
                        this.velocity = new Point2D(-50,getVelocity().getY());
                    } else {
                        checkCollision(Direction.RIGHT);
                    }
                }
                case LEFT -> {
                    if (east){
                        checkCollision(Direction.RIGHT);
                        this.velocity = new Point2D(50,getVelocity().getY());
                    } else {
                        checkCollision(Direction.LEFT);
                        this.velocity = new Point2D(-speed,getVelocity().getY());

                    }
                }
            }
        }
    }

    private boolean isInSameHeight(Platform platform){
        if (platform.getObject().getMaxY() <= getBoundingBox().getMaxY()
                && platform.getObject().getMinY() >= getBoundingBox().getMinY()){
            return true;
        } else {
            return false;
        }
    }

    public void setDir(Direction dir){
        this.dir = dir;
    }
}

