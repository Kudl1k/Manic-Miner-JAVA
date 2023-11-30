package org.kudladev;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import org.kudladev.damageable.DamageAble;
import org.kudladev.platforms.Brick;
import org.kudladev.platforms.MovingBelt;
import org.kudladev.platforms.Platform;
import org.kudladev.platforms.Trap;
import org.kudladev.utils.*;

public class Player implements DrawableObject {

    private Point2D position;
    private final Point2D size;
    private Point2D velocity;
    private final double speed;
    private boolean jumped;
    private boolean fall = false;
    private double timeInAir = 0;
    private boolean fallDamage = false;
    private Direction dir = Direction.NONE;

    private int lives = 3;
    private double air = 75;
    private int score = 0;
    private int keys = 0;

    private Platform ground;
    private final World world;

    private final Music music;

    private GameState state = GameState.RUNNING;






    private SpriteSheetList playerSprites = new SpriteSheetList();
    private int currentAnimation = 0;


    Player(World world,Music music) {
        this.world = world;
        this.size = new Point2D(25, 50);
        this.position = new Point2D(0, world.getGameSize().getY() - size.getY());
        this.velocity = new Point2D(0, 0);
        this.speed = 100;
        this.jumped = false;
        this.ground = world.getPlatforms().get(0);
        this.music = music;

        playerSprites.add(new SpriteSheetValue(0,0,10,16));
        playerSprites.add(new SpriteSheetValue(17,0, 10,16));
        playerSprites.add(new SpriteSheetValue(35,0,10,16));
        playerSprites.add(new SpriteSheetValue(53,0,10,16));
        playerSprites.add(new SpriteSheetValue(118, 0, 10,16));
        playerSprites.add(new SpriteSheetValue(101,0,10,16));
        playerSprites.add(new SpriteSheetValue(83,0,10,16));
        playerSprites.add(new SpriteSheetValue(65,0,10,16));

    }

    //SETTERS
    public void setVelocity(double x, double y) {
        this.velocity = new Point2D(x, y);
    }
    public void setGround(Platform platform){
        ground = platform;
    }
    public void setDir(Direction dir){
        this.dir = dir;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    //GETTERS
    public Point2D getVelocity() {
        return velocity;
    }
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
    public SpriteSheetList getPlayerSprites() {
        return playerSprites;
    }
    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
    }
    public double getAir() {
        return air;
    }
    public int getKeys() {
        return keys;
    }
    public double getTimeInAir(){
        return timeInAir;
    }

    public GameState getState() {
        return state;
    }

    @Override
    public void draw(GraphicsContext gc) {
        animate(dir,gc);
    }

    void jump() {
        if (!jumped) {
            this.velocity = new Point2D(velocity.getX(), -Constants.JUMPFORCE);
            this.jumped = true;
        }
    }


    public void movement(double deltaT) {
        System.out.println(keys);
        velocity = velocity.add(0, Constants.GRAVITY * deltaT);
        position = position.add(velocity.multiply(deltaT));
        if (this.onGround()){
            music.getJumpingPlayer().stop();
        }
        if (this.fall){
            music.playFalling();
        } else {
            music.getFallingPlayer().stop();
        }
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
        timeInAirTimer(deltaT);
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

    public void checkDamage(){
        for (int i = 0; i < this.world.getDamageAbles().size(); i++) {
            if (hitDamageAble(this.world.getDamageAbles().get(i))){
                music.playDie();
                respawnPlayer();
                this.world.resetModels();
                this.lives -= 1;
            }
        }
        if (timeInAir > 120){
            fallDamage = true;
        }
        if (air <= 1){
            music.playDie();
            respawnPlayer();
            this.world.resetModels();
            this.lives -=1;
        }
        if (fallDamage && onGround()){
            music.playDie();
            respawnPlayer();
            this.world.resetModels();
            this.lives -= 1;
            fallDamage = false;
        }
    }

    private void respawnPlayer(){
        this.position = new Point2D(0, world.getGameSize().getY() - size.getY());
        this.velocity = new Point2D(0, 0);
        this.keys = 0;
        this.air = 75;
        this.jumped = false;
        this.ground = world.getPlatforms().get(0);
    }

    public void restartPlayer(){
        respawnPlayer();
        this.lives = 3;
        this.score = 0;
    }

    private boolean hitDamageAble(DamageAble damageAble){
        return getBoundingBox().intersects(damageAble.getObject());
    }

    private boolean hitCollectible(Collectible collectible){
        return getBoundingBox().intersects(collectible.getObject());
    }

    public void checkCollectibles(){
        for (int i = 0; i < this.world.getCollectibles().size(); i++) {
            if (hitCollectible(this.world.getCollectibles().get(i))){
                score += 100;
                this.world.getCollectibles().remove(i);
                countKeys();
            }
        }
    }

    private void countKeys(){
        this.keys = 5-this.world.getCollectibles().size();
    }

    private void animate(Direction dir, GraphicsContext gc){
        switch (dir){
            case RIGHT -> {
                switch ((int) ((this.position.getX() % 40)/10)){
                    case 0 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(0).sourceX(), playerSprites.getSprites().get(0).sourceY(), playerSprites.getSprites().get(0).sourceWidth(), playerSprites.getSprites().get(0).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 0;
                    }
                    case 1 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(1).sourceX(), playerSprites.getSprites().get(1).sourceY(), playerSprites.getSprites().get(1).sourceWidth(), playerSprites.getSprites().get(1).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 1;
                    }
                    case 2 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(2).sourceX(), playerSprites.getSprites().get(2).sourceY(), playerSprites.getSprites().get(2).sourceWidth(), playerSprites.getSprites().get(2).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 2;
                    }
                    case 3 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(3).sourceX(), playerSprites.getSprites().get(3).sourceY(), playerSprites.getSprites().get(3).sourceWidth(), playerSprites.getSprites().get(3).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 3;
                    }
                }
            }
            case LEFT -> {
                switch ((int) ((this.position.getX() % 40)/10)){
                    case 0 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(4).sourceX(), playerSprites.getSprites().get(4).sourceY(), playerSprites.getSprites().get(4).sourceWidth(), playerSprites.getSprites().get(4).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 4;
                    }
                    case 1 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(5).sourceX(), playerSprites.getSprites().get(5).sourceY(), playerSprites.getSprites().get(5).sourceWidth(), playerSprites.getSprites().get(5).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 5;
                    }
                    case 2 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(6).sourceX(), playerSprites.getSprites().get(6).sourceY(), playerSprites.getSprites().get(6).sourceWidth(), playerSprites.getSprites().get(6).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 6;
                    }
                    case 3 -> {
                        gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(7).sourceX(), playerSprites.getSprites().get(7).sourceY(), playerSprites.getSprites().get(7).sourceWidth(), playerSprites.getSprites().get(7).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
                        this.currentAnimation = 7;
                    }
                }
            }
            case NONE -> {
                gc.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(currentAnimation).sourceX(), playerSprites.getSprites().get(currentAnimation).sourceY(), playerSprites.getSprites().get(currentAnimation).sourceWidth(), playerSprites.getSprites().get(currentAnimation).sourceHeight(), position.getX(), position.getY(), size.getX(), size.getY());
            }


        }
    }

    public void air(double deltaT){
        if (deltaT > 1){
            return;
        }
        this.air -= deltaT;
    }
    public void endGame(double deltaT){
        if (deltaT > 0.5){
            return;
        }
        if (this.air <= 1){
            this.music.getGameOverPlayer().stop();
            if (checkEnd()) {
                this.state = GameState.LOSE;
                this.world.checkHighScore();
            } else {
                this.state = GameState.WIN;
                this.world.checkHighScore();
            }
        } else {
            this.air -= deltaT * 35;
            this.score += (5*keys);
            this.music.playGameOver();
            this.state = GameState.SCORE;
        }
    }

    public boolean checkEnd(){
        return lives == 0;
    }

    private void timeInAirTimer(double deltaT){
        if (this.velocity.getY() > 0){
            this.timeInAir += (velocity.getY() * deltaT);
        } else {
            this.timeInAir = 0;
        }
    }




}

