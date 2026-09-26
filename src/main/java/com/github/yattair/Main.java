package com.github.yattair;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private static final DateTimeFormatter TIME =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        root.setStyle("""
            -fx-background-color: transparent;
        """);

        Label clock = new Label();

        clock.setTextFill(Color.WHITE);

        clock.setStyle("""
            -fx-font-family: "Segoe UI Variable";
                    -fx-font-size: 72px;
            -fx-font-weight: 300;
        """);

        updateClock(clock);

        Timeline timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> updateClock(clock)
                )
        );

        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

        root.getChildren().add(clock);

        Scene scene = new Scene(root, 600, 300);
        scene.setFill(Color.TRANSPARENT);


        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("WallSpace");
        stage.setScene(scene);

        stage.show();

        HWND hwnd = getWindowHandle(stage);

        WindowsAcrylic.enable(hwnd);
    }

    private void updateClock(Label label) {
        label.setText(LocalTime.now().format(TIME));
    }

    private HWND getWindowHandle(Stage stage) {
        // JavaFXs native window can be found through the
        // window title using the Win32 API.

        return User32.INSTANCE.FindWindow(
                null,
                stage.getTitle()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
