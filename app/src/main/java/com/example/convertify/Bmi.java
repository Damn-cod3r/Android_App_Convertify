package com.example.convertify;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Bmi extends AppCompatActivity {

    public GestureDetector gestureDetector;

    private EditText heightEditText;
    private EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        heightEditText = findViewById(R.id.height);
        weightEditText = findViewById(R.id.weight);
        Button calculateButton = findViewById(R.id.btn);


        calculateButton.setOnClickListener(v -> {
            String heightStr = heightEditText.getText().toString();
            String weightStr = weightEditText.getText().toString();

            if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                float height = Float.parseFloat(heightStr) / 100;
                float weight = Float.parseFloat(weightStr);
                float result = weight / (height * height);
                @SuppressLint("DefaultLocale") String bmiResult = String.format("%.2f", result);

                String classification = getBmiClassification(result);

                // Display BMI result and classification in a dialog
                showBmiDialog(bmiResult, classification);
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // Handle single tap (go back)
                onBackPressed();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Handle double tap (close screen)
                finish();
                return true;
            }
        });
    }

    // Function to display BMI result and classification in a dialog
    private void showBmiDialog(String bmiResult, String classification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BMI Result");
        builder.setMessage("\nYour BMI is: " + bmiResult + "\n\nClassification: " + classification);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Function to determine BMI classification
    private String getBmiClassification(float bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 30) {
            return "Overweight";
        } else if (bmi >= 30 && bmi < 35) {
            return "Obesity (Class 1)";
        } else if (bmi >= 35 && bmi < 40) {
            return "Obesity (Class 2)";
        } else {
            return "Extreme Obesity (Class 3)";
        }
    }
}
