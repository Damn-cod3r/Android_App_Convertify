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

public class Length extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextLength;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200;
    private boolean doubleTap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextLength = findViewById(R.id.editTextLength);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // Populate the spinners with length unit options
        String[] lengthUnits = {
                "Inch", "Foot", "Yard", "Nautical Mile",
                "Picometer", "Nanometer", "Micrometer",
                "Millimeter", "Centimeter", "Decimeter", "Kilometer"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onBackPressed();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (doubleTap) {
                    finish();
                } else {
                    doubleTap = true;
                    Toast.makeText(getApplicationContext(), "Double tap to exit", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> doubleTap = false, DOUBLE_TAP_TIMEOUT);
                }
                return true;
            }
        });

        buttonConvert.setOnClickListener(v -> {
            String lengthInputStr = editTextLength.getText().toString();
            double lengthInput = Double.parseDouble(lengthInputStr);

            double result = convertLength(lengthInput, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            AlertDialog.Builder builder = new AlertDialog.Builder(Length.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nLength: " + result + " " + lengthUnits[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private double convertLength(double value, int fromUnit, int toUnit) {
        double[] conversionFactors = {
                1.0,                // Inch to Inch
                12.0,               // Inch to Foot
                36.0,               // Inch to Yard
                1.0 / 1852.0,       // Inch to Nautical Mile
                0.000000001,        // Inch to Picometer
                0.0000000001,       // Inch to Nanometer
                0.000001,           // Inch to Micrometer
                0.001,              // Inch to Millimeter
                2.54,               // Inch to Centimeter
                25.4,               // Inch to Decimeter
                0.0254              // Inch to Kilometer
        };

        return value * conversionFactors[fromUnit] / conversionFactors[toUnit];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
