package com.example.convertify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Loan extends AppCompatActivity {

    GestureDetector gestureDetector;
    private EditText principalAmountEditText;
    private EditText interestRateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        principalAmountEditText = findViewById(R.id.principalAmountEditText);
        interestRateEditText = findViewById(R.id.interestRateEditText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(view -> calculateLoan());

        // Initialize GestureDetector
        // Handle single tap (e.g., go back)
        // Handle double tap (e.g., exit the activity)
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // Handle single tap (e.g., go back)
                onBackPressed();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Handle double tap (e.g., exit the activity)
                finish();
                return true;
            }
        });
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateLoan() {
        // Get the principal amount and interest rate from EditText fields
        double principalAmount = Double.parseDouble(principalAmountEditText.getText().toString());
        double interestRate = Double.parseDouble(interestRateEditText.getText().toString());

        // Calculate the monthly interest rate
        double monthlyInterestRate = (interestRate / 100) / 12;

        // Calculate the loan EMI (Equated Monthly Installment)
        int loanTermInMonths = 12; // Assuming a 1-year loan term
        double emi = (principalAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));

        // Create and configure an AlertDialog to display the EMI
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Loan EMI Calculation Result");
        builder.setMessage("Monthly EMI: ₹" + String.format("%.2f", emi));
        builder.setPositiveButton("OK", (dialog, id) -> {
            // Close the dialog when the OK button is clicked
            dialog.dismiss();
        });

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
