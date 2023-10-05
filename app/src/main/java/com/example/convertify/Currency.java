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

import java.text.DecimalFormat;

public class Currency extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText editTextAmount;

    private GestureDetector gestureDetector;
    private static final int DOUBLE_TAP_TIMEOUT = 200; // Time in milliseconds between two taps
    private boolean doubleTap = false;

    // Define conversion rates for currencies (relative to EUR)
    private static final double[] conversionRates = {
            1.0,    // EUR to EUR
            0.8574, // GBP to EUR
            1.1823, // USD to EUR
            7.4371, // DKK to EUR
            10.463, // SEK to EUR
            88.03,  // INR to EUR
            1.5597, // AUD to EUR
            1.2765, // CAD to EUR
            129.54, // JPY to EUR
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        editTextAmount = findViewById(R.id.editTextAmount);
        Button buttonConvert = findViewById(R.id.buttonConvert);

        // Populate the spinners with currency options
        String[] currencies = {"Euro (EUR)","British Pound Sterling (GBP)"," United States Dollar (USD)", "Danish Krone (DKK)","Swedish Krona (SEK)","Indian Rupee (INR)","Australian Dollar (AUD)","Canadian Dollar (CAD)","Japanese Yen (JPY)"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
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
            // Get user input from editTextAmount
            String amountStr = editTextAmount.getText().toString();
            double amount = Double.parseDouble(amountStr);

            // Perform the currency conversion based on spinner selections
            double result = convertCurrency(amount, spinnerFrom.getSelectedItemPosition(), spinnerTo.getSelectedItemPosition());

            // Format the result with two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedResult = decimalFormat.format(result);

            // Build a dialog to display the result
            AlertDialog.Builder builder = new AlertDialog.Builder(Currency.this);
            builder.setTitle("Conversion Result");
            builder.setMessage("\nAmount: " + formattedResult + " " + currencies[spinnerTo.getSelectedItemPosition()]);
            builder.setPositiveButton("OK", null); // You can add a listener if needed

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private double convertCurrency(double amount, int fromCurrencyIndex, int toCurrencyIndex) {
        double fromRate = conversionRates[fromCurrencyIndex];
        double toRate = conversionRates[toCurrencyIndex];

        // Perform the currency conversion
        return amount * toRate / fromRate;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
