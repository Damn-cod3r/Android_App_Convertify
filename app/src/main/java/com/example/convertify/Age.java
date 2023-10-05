package com.example.convertify;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Age extends AppCompatActivity {

    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        datePicker = findViewById(R.id.datePicker);

        // Get the current date
        Date currentDate = Calendar.getInstance().getTime();

        // Format the current date
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(currentDate);

        // Assuming you have a TextView with the id "currentDateTextView" in your layout
        TextView currentDateTextView = findViewById(R.id.currentDateTextView);
        currentDateTextView.setText(formattedDate);
    }


    public void calculateAge(View view) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth(); // Calendar uses 0-indexed months
        int year = datePicker.getYear();
        Calendar dob = Calendar.getInstance();
        dob.set(year, month, day);
        Calendar today = Calendar.getInstance();
        int years = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int months = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH);
        int days = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);
        if (months < 0 || (months == 0 && days < 0)) {
            years--;
            months += 12;
            if (days < 0) {
                months--;
                days += today.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        }

        String ageString = years + " Years, " + months + " Months, " + days + " Days";

        // Display age result in a dialog
        showAgeDialog(ageString);
    }

    private void showAgeDialog(String ageString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Age Result");
        builder.setMessage("\nAge : " + ageString);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
