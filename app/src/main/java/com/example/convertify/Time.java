package com.example.convertify;

import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;


public class Time extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextTime;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200; // Time in milliseconds between two taps
    private boolean doubleTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextTime = findViewById(R.id.editTextTime);
        Button buttonConvert = findViewById(R.id.buttonConvert);


        // Populate the spinners with time unit options
        String[] timeUnits = {"Picoseconds", "Nanoseconds", "Milliseconds", "Seconds", "Minutes", "Hours", "Days", "Years"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // Handle single tap (go back)
                onBackPressed();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Handle double tap (exit the app)
                if (doubleTap) {
                    finish(); // Exit the app
                } else {
                    doubleTap = true;
                    Toast.makeText(getApplicationContext(), "Double tap to exit", Toast.LENGTH_SHORT).show();
                    // Reset the double tap flag after a timeout
                    new Handler().postDelayed(() -> doubleTap = false, DOUBLE_TAP_TIMEOUT);
                }
                return true;
            }
        });

        buttonConvert.setOnClickListener(v -> {
            // Get user input from editTextTime
            String timeInputStr = editTextTime.getText().toString();
            double timeInput = Double.parseDouble(timeInputStr);

            // Perform the conversion based on spinner selections
            double result = convertTime(timeInput, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            // Build a dialog to display the result
            AlertDialog.Builder builder = new AlertDialog.Builder(Time.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nConverted Time: " + result + " " + timeUnits[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null); // You can add a listener if needed

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    private double convertTime(double value, int fromUnit, int toUnit) {
        // Define conversion factors for various time units to seconds
        double[] conversionFactors = {
                1.0,                // Picoseconds to seconds
                1e-9,               // Nanoseconds to seconds
                1e-3,               // Milliseconds to seconds
                1.0,                // Seconds to seconds
                60.0,               // Minutes to seconds
                3600.0,             // Hours to seconds
                86400.0,            // Days to seconds
                31536000.0          // Years to seconds
        };

        // Perform the conversion

        return value * conversionFactors[fromUnit] / conversionFactors[toUnit];
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
