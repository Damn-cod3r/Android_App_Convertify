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

public class Mass extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextWeight;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200; // Time in milliseconds between two taps
    private boolean doubleTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextWeight = findViewById(R.id.editTextWeight);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // Populate the spinners with weight unit options
        String[] weightUnits = getResources().getStringArray(R.array.weight_units);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightUnits);
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
            // Get user input from editTextWeight
            String weightInputStr = editTextWeight.getText().toString();
            double weightInput = Double.parseDouble(weightInputStr);

            // Perform the conversion based on spinner selections
            double result = convertWeight(weightInput, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            // Build a dialog to display the result
            AlertDialog.Builder builder = new AlertDialog.Builder(Mass.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nWeight: " + result + " " + weightUnits[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null); // You can add a listener if needed

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private double convertWeight(double value, int fromUnit, int toUnit) {
        // Define conversion factors for various weight units to Jin (Taiwan)
        double[] conversionFactors = {
                1.0,             // Jin (Taiwan) to Jin (Taiwan)
                0.5,             // Kilogram to Jin (Taiwan)
                0.0005,          // Tonne to Jin (Taiwan)
                1.60934,         // Pound to Jin (Taiwan)
                50.0,            // Ounce to Jin (Taiwan)
                160.0,           // Carat to Jin (Taiwan)
                500.0,           // Grain to Jin (Taiwan)
                0.000446429,     // Long Ton to Jin (Taiwan)
                0.0005,          // Short Ton to Jin (Taiwan)
                5.0,             // Quintal to Jin (Taiwan)
                500000.0,        // Microgram to Jin (Taiwan)
                500.0,           // Milligram to Jin (Taiwan)
                0.001            // Gram to Jin (Taiwan)
        };

        // Perform the conversion
        return value * conversionFactors[fromUnit] / conversionFactors[toUnit];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
