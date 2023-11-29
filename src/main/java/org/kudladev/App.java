package org.kudladev;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import org.kudladev.utils.Constants;

public class App extends Application {

    private AnimationTimer timer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            StackPane root = new StackPane();
            root.setAlignment(Pos.CENTER); // Sets alignment of children in StackPane
            VBox upperVBox = new VBox(); // VBox for gameCanvas and infoCanvas
            upperVBox.setAlignment(Pos.CENTER); // Sets alignment of children in VBox
            Canvas lowerCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            Canvas gameCanvas = new Canvas(Constants.GAME_WINDOW_WIDTH, Constants.GAME_HEIGHT);
            Canvas infoCanvas = new Canvas(Constants.GAME_WINDOW_WIDTH, Constants.INFO_HEIGHT);

            upperVBox.getChildren().addAll(gameCanvas, infoCanvas); // add gameCanvas and infoCanvas to VBox
            root.getChildren().addAll(lowerCanvas, upperVBox); // add lowerCanvas and VBox to StackPane



            Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/fontstyle.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("Manic Miner - KUD0132");
            primaryStage.getIcons().add(new Image("manicminer.png"));
            primaryStage.show();

            // Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);

            timer = new DrawingThread(gameCanvas,infoCanvas,lowerCanvas); // Assuming a DrawingThread class accepting the canvases
            timer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exitProgram(WindowEvent windowEvent) {
        System.exit(0);
    }
}