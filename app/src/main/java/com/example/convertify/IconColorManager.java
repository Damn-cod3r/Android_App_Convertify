package com.example.convertify;

import android.content.res.Configuration;
import android.graphics.Color;
import android.widget.ImageView;

public class IconColorManager {

    public static void updateIconsBasedOnNightMode(Configuration configuration, ImageView... icons) {
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int targetColor;

        // Determine the target color based on night mode
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode is active
            targetColor = Color.WHITE;
        } else {
            // Light mode is active
            targetColor = Color.BLACK;
        }

        // Apply the target color to each icon
        for (ImageView icon : icons) {
            icon.setColorFilter(targetColor);
        }
    }
}

