package com.example.convertify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Discount extends AppCompatActivity {

    GestureDetector gestureDetector;
    private EditText originalPriceEditText;
    private EditText discountPercentageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        originalPriceEditText = findViewById(R.id.originalPriceEditText);
        discountPercentageEditText = findViewById(R.id.discountPercentageEditText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(view -> calculateDiscount());

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

    @SuppressLint("SetTextI18n")
    private void calculateDiscount() {
        // Get the original price and discount percentage from EditText fields
        double originalPrice = Double.parseDouble(originalPriceEditText.getText().toString());
        double discountPercentage = Double.parseDouble(discountPercentageEditText.getText().toString());

        // Calculate the discount amount
        double discountAmount = (originalPrice * discountPercentage) / 100;

        // Calculate the final price and amount saved
        double finalPrice = originalPrice - discountAmount;
        double amountSaved = originalPrice - finalPrice;

        // Create and configure an AlertDialog to display the results
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Discount Calculation Results");
        builder.setMessage("\nDiscount Amount: ₹" + discountAmount + "\n" +
                "\nFinal Price: ₹" + finalPrice + "\n" +
                "\nAmount Saved: ₹" + amountSaved);
        builder.setPositiveButton("OK", (dialog, id) -> {
            // Close the dialog when the OK button is clicked
            dialog.dismiss();
        });

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
