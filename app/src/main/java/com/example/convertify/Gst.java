package com.example.convertify;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Gst extends AppCompatActivity {

    private EditText editTextAmount, editTextGST;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextGST = findViewById(R.id.editTextGST);

        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(v -> calculateGST());
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(v -> clearFields());

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onBackPressed(); // Go back on single tap
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                finish(); // Exit the app on double tap
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private void calculateGST() {
        // Get user input
        String amountText = editTextAmount.getText().toString();
        String gstText = editTextGST.getText().toString();

        // Convert input to numbers
        double amount = Double.parseDouble(amountText);
        double gstRate = Double.parseDouble(gstText);

        // Calculate GST
        double gstAmount = (amount * gstRate) / 100.0;
        double total = amount + gstAmount;

        // Display the result in a pop-up dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GST Calculation Result");
        builder.setMessage("\nGST Amount: " + gstAmount + "\n\nTotal Amount: " + total);
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Do nothing (dismiss the dialog)
        });
        builder.show();

    }
    private void clearFields() {
        editTextAmount.getText().clear();
        editTextGST.getText().clear();
    }
}
