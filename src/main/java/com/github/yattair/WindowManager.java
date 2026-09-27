package com.github.yattair;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

import com.sun.jna.platform.win32.WinUser;
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
            if (shellView != null) {
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

    public static void reparentWindow(HWND javafxhwnd, HWND workerw){
        if(javafxhwnd == null || workerw == null) {
            System.out.println("1");
            return;
        }
        System.out.println("didnt return");
        User32.INSTANCE.SetParent(javafxhwnd, workerw);
    }

    public static void makeUnminimizable(HWND hwnd) {
        if (hwnd == null) {
            return;
        }

        int style = User32.INSTANCE.GetWindowLong(
                hwnd,
                WinUser.GWL_STYLE
        );

        // Remove minimize button
        style &= ~WinUser.WS_MINIMIZEBOX;

        User32.INSTANCE.SetWindowLong(
                hwnd,
                WinUser.GWL_STYLE,
                style
        );

        // Tell Windows that the frame/style has changed
        User32.INSTANCE.SetWindowPos(
                hwnd,
                null,
                0, 0, 0, 0,
                WinUser.SWP_NOMOVE |
                        WinUser.SWP_NOSIZE |
                        WinUser.SWP_NOZORDER |
                        WinUser.SWP_FRAMECHANGED
        );
    }

}
