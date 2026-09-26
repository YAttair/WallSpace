package com.github.yattair;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

public class WindowsAcrylic {

    private interface User32Ex extends Library {

        User32Ex INSTANCE = Native.load(
                "user32",
                User32Ex.class
        );

        boolean SetWindowCompositionAttribute(
                HWND hwnd,
                WindowCompositionAttributeData data
        );
    }

    private interface Dwmapi extends Library {

        Dwmapi INSTANCE = Native.load(
                "dwmapi",
                Dwmapi.class
        );

        int DwmSetWindowAttribute(
                HWND hwnd,
                int attribute,
                Pointer value,
                int size
        );
    }


    private static final int WCA_ACCENT_POLICY = 19;

    private static final int ACCENT_ENABLE_ACRYLICBLURBEHIND = 4;

    //corner variables
    private static final int DWMWA_CORNER_PREF = 33;
    private static final int DWMWA_ROUND = 2;

    public static class AccentPolicy extends Structure {

        public int AccentState;

        public int AccentFlags;

        public int GradientColor;

        public int AnimationId;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(
                    "AccentState",
                    "AccentFlags",
                    "GradientColor",
                    "AnimationId"
            );
        }
    }

    public static class WindowCompositionAttributeData
            extends Structure {

        public int Attribute;

        public Pointer Data;

        public int SizeOfData;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(
                    "Attribute",
                    "Data",
                    "SizeOfData"
            );
        }
    }

    public static void enable(HWND hwnd) {

        AccentPolicy policy = new AccentPolicy();

        policy.AccentState =
                ACCENT_ENABLE_ACRYLICBLURBEHIND;

        policy.AccentFlags = 0;

        /*
         * AABBGGRR
         *
         * The alpha value controls opacity. (0-255 in HEXA)
         *
         * Example:
         *
         * 0xCCFFFFFF
         *
         * = white with ~80% alpha
         *
         */

        policy.GradientColor = 0x66202020;

        policy.AnimationId = 0;

        policy.write();

        WindowCompositionAttributeData data =
                new WindowCompositionAttributeData();

        data.Attribute = WCA_ACCENT_POLICY;
        data.Data = policy.getPointer();
        data.SizeOfData = policy.size();

        data.write();

        User32Ex.INSTANCE.SetWindowCompositionAttribute(
                hwnd,
                data
        );

        //enable roundness
        IntByReference cornerPreference = new IntByReference(DWMWA_ROUND);

        Dwmapi.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                DWMWA_CORNER_PREF,
                cornerPreference.getPointer(),
                Integer.BYTES
        );
    }
}

