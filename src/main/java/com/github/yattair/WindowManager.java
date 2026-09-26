package com.github.yattair;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import javafx.stage.Stage;
public class WindowManager {
    public static HWND getHandle(Stage stage) {
        return User32.INSTANCE.FindWindow(
                null,
                stage.getTitle()
        );
    }
}
