package com.github.yattair;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Clock {

    private static final String LANG = "ar-SY";

    private static final Locale LOCALE =
            Locale.forLanguageTag(LANG);

    private static final DateTimeFormatter TIME =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final DateTimeFormatter DATE =
            DateTimeFormatter.ofPattern(
                    "EEEE dd MMMM yyyy",
                    LOCALE
            );

    private final Text clockText;
    private final Text dateText;

    public Clock() {

        Font.loadFont(
                getClass().getResourceAsStream(
                        "/fonts/Tajawal/Tajawal-Bold.ttf"
                ),
                72
        );

        clockText = new Text();
        clockText.getStyleClass().add("clock");

        dateText = new Text();
        dateText.getStyleClass().add("date");
    }

    public void update() {

        String time = LocalTime.now().format(TIME);
        String date = LocalDate.now().format(DATE);

        if (LANG.contains("ar")) {
            time = convertDigits(time);
            date = convertDigits(date);
        }

        clockText.setText(time);
        dateText.setText(date);
    }

    public Text getClockText() {
        return clockText;
    }

    public Text getDateText() {
        return dateText;
    }

    private String convertDigits(String text) {
        return text
                .replace('0', '٠')
                .replace('1', '١')
                .replace('2', '٢')
                .replace('3', '٣')
                .replace('4', '٤')
                .replace('5', '٥')
                .replace('6', '٦')
                .replace('7', '٧')
                .replace('8', '٨')
                .replace('9', '٩');
    }
}
