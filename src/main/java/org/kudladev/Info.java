package org.kudladev;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
        System.out.println(this.airProgressBar.getWidth());
        System.out.println(this.world.getPlayer().getAir()/100);
        this.airProgressBar = new Rectangle2D(this.airProgressBar.getMinX(),this.airProgressBar.getMinY(),maxAir*(this.world.getPlayer().getAir()/90),this.airProgressBar.getHeight());
    }


}
