package com.github.yattair;

import com.sun.jna.platform.win32.WinDef.HWND;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Clock clock = new Clock();

        VBox timeStuff = new VBox(2);
        timeStuff.setAlignment(Pos.CENTER);
        timeStuff.getChildren().addAll(
                clock.getClockText(),
                clock.getDateText()
        );

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(timeStuff);

        clock.update();

        Timeline timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> clock.update()
                )
        );

        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/style.css")
                        .toExternalForm()
        );

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("WallSpace");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        HWND hwnd = WindowManager.getHandle(stage);

        WindowsAcrylic.enable(hwnd);

        WindowManager.reparentWindow(hwnd, WindowManager.getWorkerW());
    }

    public static void main(String[] args) {
        launch(args);
    }
}