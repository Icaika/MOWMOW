package com.myapp.rentalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;

public class CalenderForBooking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander_for_booking);
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.thisMonthInUtcMilliseconds())).build();
        materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
        materialDatePicker.addOnPositiveButtonClickListener(o->{
            TextView textView = (TextView) findViewById(R.id.selectedDate);
            textView.setText(materialDatePicker.getHeaderText());
        });



    }
}