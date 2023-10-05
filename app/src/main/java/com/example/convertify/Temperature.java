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

public class Temperature extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextTemperature;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200; // Time in milliseconds between two taps
    private boolean doubleTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextTemperature = findViewById(R.id.editTextTemperature);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // Populate the spinners with temperature unit options
        String[] temperatureUnits = {"Reaumur (Re)", "Rankine (R)", "Kelvin (K)", "Fahrenheit (F)", "Celsius (C)"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, temperatureUnits);
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
            // Get user input from editTextTemperature
            String temperatureInputStr = editTextTemperature.getText().toString();
            double temperatureInput;
            try {
                temperatureInput = Double.parseDouble(temperatureInputStr);
            } catch (NumberFormatException e) {
                // Handle invalid input
                temperatureInput = 0; // Default value
            }

            // Perform the conversion based on spinner selections
            double result = convertTemperature(temperatureInput, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            // Build a dialog to display the result
            AlertDialog.Builder builder = new AlertDialog.Builder(Temperature.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nTemperature: " + result + " " + temperatureUnits[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null); // You can add a listener if needed

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private double convertTemperature(double value, int fromUnit, int toUnit) {
        // Define conversion factors for various temperature units
        double[] conversionFactors = {
                // Conversion to Kelvin
                5.0 / 4.0,      // Reaumur to Kelvin
                5.0 / 9.0,      // Rankine to Kelvin
                1.0,            // Kelvin to Kelvin
                5.0 / 9.0,      // Fahrenheit to Kelvin
                1.0,            // Celsius to Kelvin
        };

        // Perform the conversion
        double kelvinValue = value * conversionFactors[fromUnit];

        // Convert from Kelvin to the target unit
        switch (toUnit) {
            case 0: // Reaumur
                return (4.0 / 5.0) * kelvinValue;
            case 1: // Rankine
                return (9.0 / 5.0) * kelvinValue;
            case 2: // Kelvin
                return kelvinValue;
            case 3: // Fahrenheit
                return (9.0 / 5.0) * (kelvinValue - 273.15) + 32;
            case 4: // Celsius
                return kelvinValue - 273.15;
            default:
                return 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
