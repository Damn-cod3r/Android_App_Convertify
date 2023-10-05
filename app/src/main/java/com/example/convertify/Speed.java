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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Speed extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextSpeed;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200; // Time in milliseconds between two taps
    private boolean doubleTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextSpeed = findViewById(R.id.editTextSpeed);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // Populate the spinners with speed unit options
        String[] speedUnits = {
                "Inch per sec ips", "Foot per sec fps", "Mile per sec mps", "Knot k", "Kilometer per sec kps",
                "Kilometer per hour kph", "Meter per second mps", "Mach m", "Lightspeed c"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, speedUnits);
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
            // Get user input from editTextSpeed
            String speedInputStr = editTextSpeed.getText().toString();
            double speedInput = Double.parseDouble(speedInputStr);

            // Perform the conversion based on spinner selections
            double result = convertSpeed(speedInput, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            // Build a dialog to display the result
            AlertDialog.Builder builder = new AlertDialog.Builder(Speed.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nSpeed: " + result + " " + speedUnits[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null); // You can add a listener if needed

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private double convertSpeed(double value, int fromUnit, int toUnit) {
        // Define conversion factors for various speed units to meters per second
        double[] conversionFactors = {
                0.0254,         // Inch per sec to m/s
                0.3048,         // Foot per sec to m/s
                1609.34,        // Mile per sec to m/s
                0.514444,       // Knot to m/s
                1000.0,         // Kilometer per sec to m/s
                0.277778,       // Kilometer per hour to m/s
                1.0,            // Meter per second to m/s
                343.0,          // Mach to m/s
                299792458.0     // Lightspeed to m/s
        };

        // Perform the conversion
        return value * conversionFactors[fromUnit] / conversionFactors[toUnit];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
