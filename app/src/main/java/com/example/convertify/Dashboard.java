package com.example.convertify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import pl.droidsonroids.gif.GifImageButton;

public class Dashboard extends AppCompatActivity {
    GifImageButton age, gst, currency, area, discount, length, loans, speed, temperature, time, weight, bmi;
    GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        area = findViewById(R.id.area);
        discount = findViewById(R.id.discount);
        length = findViewById(R.id.length);
        loans = findViewById(R.id.loan);
        speed = findViewById(R.id.speed);
        temperature = findViewById(R.id.temperature);
        bmi = findViewById(R.id.bmi);
        time = findViewById(R.id.time);
        age = findViewById(R.id.age);
        gst = findViewById(R.id.GST);
        currency = findViewById(R.id.currency);
        weight = findViewById(R.id.weight);

        // Set night mode to follow the system
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        // Check and update the icons' colors based on the current night mode
        updateIconsBasedOnNightMode();

        gst.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Gst.class);
            startActivity(intent);
        });
        currency.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Currency.class);
            startActivity(intent);

        });
        weight.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Mass.class);
            startActivity(intent);
        });
        age.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Age.class);
            startActivity(intent);
        });
        area.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Area.class);
            startActivity(intent);
        });
        discount.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Discount.class);
            startActivity(intent);

        });
        length.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Length.class);
            startActivity(intent);
        });
        loans.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Loan.class);
            startActivity(intent);

        });
        speed.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Speed.class);
            startActivity(intent);

        });
        time.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Time.class);
            startActivity(intent);

        });
        temperature.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Temperature.class);
            startActivity(intent);

        });
        bmi.setOnClickListener(view -> {
            Intent intent = new Intent(Dashboard.this, Bmi.class);
            startActivity(intent);

        });


        // Add a double-tap gesture to close the app
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                finish();
                return true;
            }
        });

        // Set a touch listener on the root layout of your activity
        View rootLayout = findViewById(android.R.id.content);
        rootLayout.setOnTouchListener((view, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        });


            }

    private void updateIconsBasedOnNightMode() {
        Configuration configuration = getResources().getConfiguration();
        IconColorManager.updateIconsBasedOnNightMode(configuration, weight, age, gst, currency, area, discount, length, loans, speed, temperature, time, bmi);
    }
}
