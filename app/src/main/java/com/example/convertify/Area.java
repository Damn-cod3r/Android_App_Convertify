package com.example.convertify;

import android.annotation.SuppressLint;
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

public class Area extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextArea;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200; // Time in milliseconds between two taps
    private boolean doubleTap = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextArea = findViewById(R.id.editTextArea);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // Populate the spinners with area unit options
        String[] areaUnits = {"Square Meter (m²)", "Square Kilometer (km²)", "Square Mile (mi²)", "Acre", "Hectare (ha)", "Square Foot (ft²)", "Square Yard (yd²)", "Square Inch (in²)", "Square Millimeter (mm²)"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areaUnits);
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
            // Get user input from editTextArea
            String areaInputStr = editTextArea.getText().toString();
            double areaInput = Double.parseDouble(areaInputStr);

            // Perform the conversion based on spinner selections
            double result = convertArea(areaInput, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            // Build a dialog to display the result
            AlertDialog.Builder builder = new AlertDialog.Builder(Area.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nConverted Area: " + result + " " + areaUnits[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null); // You can add a listener if needed

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private double convertArea(double value, int fromUnit, int toUnit) {
        // Define conversion factors for various area units to square meters
        double[] conversionFactors = {
                1.0,              // Square Meter (m²) to square meters
                1.0e6,            // Square Kilometer (km²) to square meters
                2.59e6,           // Square Mile (mi²) to square meters
                4046.86,          // Acre to square meters
                10000.0,          // Hectare (ha) to square meters
                0.0929,           // Square Foot (ft²) to square meters
                0.8361,           // Square Yard (yd²) to square meters
                0.0006452,        // Square Inch (in²) to square meters
                1.0e-6            // Square Millimeter (mm²) to square meters
        };

        // Perform the conversion
        return value * conversionFactors[fromUnit] / conversionFactors[toUnit];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
