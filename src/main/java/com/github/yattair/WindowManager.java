package com.github.yattair;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

import javafx.stage.Stage;
public class WindowManager {

    public static HWND getHandle(Stage stage) {
        return User32.INSTANCE.FindWindow(
                null,
                stage.getTitle()
        );
    }

    public static HWND getWorkerW() {
        HWND progman = User32.INSTANCE.FindWindow("Progman", null);

        if(progman == null) return null;

        User32.INSTANCE.SendMessage(
                progman, 0x052C,
                new WinDef.WPARAM(0), new WinDef.LPARAM(0));

        final HWND[] workerw = new HWND[1];

        User32.INSTANCE.EnumWindows((hwnd, data) -> {
            HWND shellView = User32.INSTANCE.FindWindowEx(
                    hwnd,
                    null,
                    "SHELLDLL_DefView",
                    null

            );
            if (shellView == null) {
                workerw[0] = User32.INSTANCE.FindWindowEx(
                        null,
                        hwnd,
                        "WorkerW",
                        null
                );
            }
            return workerw[0] == null;
        }, null);

        return workerw[0];
    }
}
