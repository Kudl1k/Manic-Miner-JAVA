package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kudladev.utils.Constants;
import org.kudladev.utils.SpriteSheetList;
import org.kudladev.utils.SpriteSheetValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Info {

    private World world;
    private Rectangle2D airProgressBar;
    private double maxAir;
    private Canvas infoCanvas;

    public Info(Canvas ic, World world){
        this.world = world;
        this.infoCanvas = ic;
        airProgressBar = new Rectangle2D(100,35,infoCanvas.getWidth()-105,20);
        maxAir = infoCanvas.getWidth()-105;
    }

    public void draw(GraphicsContext ic){
        ic.clearRect(0,0,infoCanvas.getWidth(),infoCanvas.getHeight());
        ic.setFill(Color.BLACK);
        ic.fillRect(0,0,infoCanvas.getWidth(),infoCanvas.getHeight());
        header(ic);
        airDisplay(ic);
        displayHighScore(ic);
        displayScore(ic);
        displayLives(ic);
    }

    private void header(GraphicsContext ic){
        ic.setFill(Color.YELLOW);
        ic.fillRect(0,0,infoCanvas.getWidth(),30);
        ic.setFill(Color.BLACK);
        ic.setFont(Font.font("Dogica Pixel", FontWeight.NORMAL,23));
        ic.fillText("Central Cavern",(infoCanvas.getWidth()/2)-140,25);
    }

    private void airDisplay(GraphicsContext ic){
        ic.setFill(Color.RED);
        ic.fillRect(0,30,infoCanvas.getWidth()/3,30);
        ic.setFill(Color.LIME);
        ic.fillRect(infoCanvas.getWidth()/3,30,(infoCanvas.getWidth()/3)*2,30);
        ic.setFill(Color.WHITE);
        ic.setFont(Font.font("Dogica Pixel", FontWeight.NORMAL,23));
        ic.fillText("AIR",10,55);
        adjustAirDisplay();
        ic.fillRect(airProgressBar.getMinX(),airProgressBar.getMinY(),airProgressBar.getWidth(),airProgressBar.getHeight());
    }

    private void adjustAirDisplay(){
        System.out.println(maxAir*(this.world.getPlayer().getAir()/75));
        this.airProgressBar = new Rectangle2D(this.airProgressBar.getMinX(),this.airProgressBar.getMinY(),maxAir*(this.world.getPlayer().getAir()/75),this.airProgressBar.getHeight());
    }

    private void displayHighScore(GraphicsContext ic){
        ic.setFill(Color.YELLOW);
        ic.setFont(Font.font("Dogica Pixel", FontWeight.NORMAL,23));
        String formatted = String.format("%06d", this.world.loadScoreFromFile());
        ic.fillText("High Score  " + formatted,0,100);
    }

    private void displayScore(GraphicsContext ic){
        ic.setFill(Color.YELLOW);
        ic.setFont(Font.font("Dogica Pixel", FontWeight.NORMAL,23));
        String formatted = String.format("%06d", this.world.getPlayer().getScore());
        ic.fillText("Score  " + formatted,infoCanvas.getWidth()-280,100);
    }

    private void displayLives(GraphicsContext ic){
        SpriteSheetList playerSprites = this.world.getPlayer().getPlayerSprites();
        for (int i = 1; i < this.world.getPlayer().getLives(); i++) {
            if (i != 1){
                i += 1;
            }
            ic.drawImage(Constants.SPRITESHEET, playerSprites.getSprites().get(0).sourceX(), playerSprites.getSprites().get(0).sourceY(), playerSprites.getSprites().get(0).sourceWidth(), playerSprites.getSprites().get(0).sourceHeight(), 20*i, 120, 25, 50);
        }
    }




}
