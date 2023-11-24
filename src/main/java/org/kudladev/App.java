package org.kudladev;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.kudladev.utils.Constants;

public class App extends Application {

    private AnimationTimer timer;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            Canvas gameCanvas= new Canvas(Constants.WINDOW_WIDTH,Constants.GAME_HEIGHT);
            Canvas infoCanvas= new Canvas(Constants.WINDOW_WIDTH,Constants.INFO_HEIGHT);
            infoCanvas.setLayoutY(Constants.GAME_HEIGHT);
            root.getChildren().add(gameCanvas);
            root.getChildren().add(infoCanvas);
            Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("Manic Miner - KUD0132");
            primaryStage.getIcons().add(new Image("manicminer.png"));
            primaryStage.show();

            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
            timer = new DrawingThread(gameCanvas,infoCanvas);
            timer.start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exitProgram(WindowEvent windowEvent) {
        System.exit(0);
    }


}