package com.github.yattair;


import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

import com.sun.jna.platform.win32.WinUser;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class WindowManager {

    public static HWND getHandle(Stage stage) {
        return User32.INSTANCE.FindWindow(
                null,
                stage.getTitle()
        );
    }

    public static HWND getWorkerW() {

        // nice help: https://dynamicwallpaper.readthedocs.io/en/docs/dev/make-wallpaper.html

        HWND progman = User32.INSTANCE.FindWindow("Progman", null);

        if(progman == null) return null;

        User32.INSTANCE.SendMessage(
                progman, 0x052C,
                new WinDef.WPARAM(0xD), new WinDef.LPARAM(0));
        User32.INSTANCE.SendMessage(
                progman, 0x052C,
                new WinDef.WPARAM(0xD), new WinDef.LPARAM(1));

        AtomicReference<WinDef.HWND> workerRef = new AtomicReference<>();
        User32.INSTANCE.EnumWindows((hWnd, data) -> {
            if(User32.INSTANCE.FindWindowEx(hWnd, null, "SHELLDLL_DefView", null)==null)
                return true;

            HWND worker = User32.INSTANCE.FindWindowEx(null, hWnd, "WorkerW", null);
            if(worker != null)
            {
                workerRef.set(worker);
            }

            return true;
        }, null);
        return workerRef.get();

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
