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
                new WinDef.WPARAM(0xD), new WinDef.LPARAM(0));
        User32.INSTANCE.SendMessage(
                progman, 0x052C,
                new WinDef.WPARAM(0xD), new WinDef.LPARAM(1));



        return new HWND();
    }

    public static void reparentWindow(HWND javafxhwnd, HWND workerw){
        if(javafxhwnd == null || workerw == null) {
            System.out.println("1");
            return;
        }
        System.out.println("didnt return");
        User32.INSTANCE.SetParent(javafxhwnd, workerw);
    }

}
