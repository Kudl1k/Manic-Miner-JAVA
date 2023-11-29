package org.kudladev.utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kudladev.World;

public class Screens{

    private World world;


    public Screens(World world){
        this.switcher = false;
        this.elapsed = 0;
        this.world = world;
    }

    private final double widthOfScreen = Constants.WINDOW_WIDTH;
    private final double heightOfScreen = Constants.WINDOW_HEIGHT;

    private boolean switcher;
    private double elapsed;


    public void loadingScreen(GraphicsContext lc){
        lc.clearRect(0,0, widthOfScreen, heightOfScreen);
        lc.setFill(Color.BLACK);
        lc.fillRect(0,0,widthOfScreen,heightOfScreen);
        if (switcher){
            wordManic(lc);
        } else {
            wordMiner(lc);
        }
    }

    public void mainScreen(GraphicsContext lc){
        lc.clearRect(0,0, widthOfScreen, heightOfScreen);
        lc.setFill(Color.BLACK);
        lc.fillRect(0,0, widthOfScreen,heightOfScreen);
        lc.drawImage(Constants.MAINSCREEN,0,0,Constants.WINDOW_WIDTH,Constants.GAME_HEIGHT+60);
    }


    private void wordManic(GraphicsContext lc){
        drawLetter('M',((widthOfScreen/2)-(45*5)-10),(heightOfScreen/2),Color.RED,lc);
        drawLetter('A',((widthOfScreen/2)-(45*3)),(heightOfScreen/2)+20,Color.YELLOW,lc);
        drawLetter('N',widthOfScreen/2-45,heightOfScreen/2,Color.LIME,lc);
        drawLetter('I',(widthOfScreen/2)+(45*1),heightOfScreen/2+30,Color.CYAN,lc);
        drawLetter('C',((widthOfScreen/2)+(45*2)+15),(heightOfScreen/2)+20,Color.DEEPPINK,lc);
    }

    private void wordMiner(GraphicsContext lc){
        drawLetter('M',((widthOfScreen/2)-(45*5)+10),(heightOfScreen/2)+20,Color.CYAN,lc);
        drawLetter('I',((widthOfScreen/2)-(45*2)-25),(heightOfScreen/2)-10,Color.DEEPPINK,lc);
        drawLetter('N',widthOfScreen/2-45,heightOfScreen/2,Color.RED,lc);
        drawLetter('E',(widthOfScreen/2)+(45*1),(heightOfScreen/2)-10,Color.YELLOW,lc);
        drawLetter('R',((widthOfScreen/2)+(45*3)),(heightOfScreen/2)+20,Color.LIME,lc);
    }

    private void drawLetter(char letter, double x, double y,Color color,GraphicsContext lc){
        lc.setFill(color);
        lc.setFont(Font.font("Dogica Pixel", FontWeight.NORMAL,90));
        lc.fillText(String.valueOf(letter),x,y);
    }

    public void clock(double deltaT){
        this.elapsed += deltaT;
        if (this.elapsed >0.5){
            this.switcher = !this.switcher;
            this.elapsed = 0;
        }
    }

    public void endScreen(GraphicsContext lc){
        lc.clearRect(0,0, widthOfScreen, heightOfScreen);
        lc.setFill(Color.BLACK);
        lc.fillRect(0,0,widthOfScreen,heightOfScreen);
        lc.setFill(Color.WHITE);
        lc.setFont(Font.font("Dogica Pixel", FontWeight.NORMAL,30));
        lc.fillText("Highscore: " + this.world.loadScoreFromFile(),widthOfScreen/2-200,heightOfScreen/2);
        lc.fillText("Your score: " + this.world.getPlayer().getScore(),widthOfScreen/2-200,heightOfScreen/2+50);

    }


}
