package com.myapp.rentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class VehicleCategorySelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_category_selection);

        /*Hide notification bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        //Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button redBikeQty = (Button) findViewById(R.id.bike_reduce_qty);
        Button incBikeQty = (Button) findViewById(R.id.bike_inc_qty);
        Button redCarQty = (Button) findViewById(R.id.car_reduce_qty);
        Button incCarQty = (Button) findViewById(R.id.car_inc_qty);
        Button redScootyQty = (Button) findViewById(R.id.scooty_reduce_qty);
        Button incScootyQty = (Button) findViewById(R.id.scooty_inc_qty);

        //Quantity buttons for bike

        redBikeQty.setOnClickListener(v -> {
            TextView bikeQty = (TextView) findViewById(R.id.bike_qty_selected);
            int val = Integer.parseInt(bikeQty.getText().toString());
            if (val > 0) {
                val--;
                bikeQty.setText(String.valueOf(val));
            }
        });
        incBikeQty.setOnClickListener(v -> {
            TextView bikeQty = (TextView) findViewById(R.id.bike_qty_selected);
            int val = Integer.parseInt(bikeQty.getText().toString());
            if (val < 12) {
                val++;
                TextView bQty = (TextView) findViewById(R.id.bike_qty_selected);
                bQty.setText(String.valueOf(val));
            } else
                Toast.makeText(this, "Cannot select more than 12 " +
                        "vehicles of this type at the moment.", Toast.LENGTH_SHORT).show();
        });

        //Quantity buttons for car

        redCarQty.setOnClickListener(v -> {
            TextView carQty = (TextView) findViewById(R.id.car_qty_selected);
            int val = Integer.parseInt(carQty.getText().toString());
            if (val > 0) {
                val--;
                carQty.setText(String.valueOf(val));
            }
        });
        incCarQty.setOnClickListener(v -> {
            TextView carQty = (TextView) findViewById(R.id.car_qty_selected);
            int val = Integer.parseInt(carQty.getText().toString());
            if (val < 12) {
                val++;
                carQty.setText(String.valueOf(val));
            } else
                Toast.makeText(this, "Cannot select more than 12 " +
                        "vehicles of this type at the moment.", Toast.LENGTH_SHORT).show();
        });

        //Quantity buttons for scooty

        redScootyQty.setOnClickListener(v -> {
            TextView scootyQty = (TextView) findViewById(R.id.scooty_qty_selected);
            int val = Integer.parseInt(scootyQty.getText().toString());
            if (val > 0) {
                val--;
                scootyQty.setText(String.valueOf(val));
            }
        });
        incScootyQty.setOnClickListener(v -> {
            TextView scootyQty = (TextView) findViewById(R.id.scooty_qty_selected);
            int val = Integer.parseInt(scootyQty.getText().toString());
            if (val < 12) {
                val++;
                scootyQty.setText(String.valueOf(val));
            } else
                Toast.makeText(this, "Cannot select more than 12 " +
                        "vehicles of this type at the moment.", Toast.LENGTH_SHORT).show();
        });

        //back button to signOut
        //TODO fix pressing back issue to log in
        ImageButton signOutTemp = (ImageButton) findViewById(R.id.vehicleCategoryBackBtn);
        signOutTemp.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        Button selectBookingDates = (Button) findViewById(R.id.selectBookingDate);
        selectBookingDates.setOnClickListener(v->{
            TextView bikeQty = (TextView) findViewById(R.id.bike_qty_selected);
            TextView carQty = (TextView) findViewById(R.id.car_qty_selected);
            TextView scootyQty = (TextView) findViewById(R.id.scooty_qty_selected);
            int val = Integer.parseInt(bikeQty.getText().toString()) + Integer.parseInt(carQty.getText().toString())
                    + Integer.parseInt(scootyQty.getText().toString());
            if (val>0){
                Intent i = new Intent(this, CalenderForBooking.class);
                startActivity(i);}
            else{
                Toast.makeText(this, "Select a vehicle first!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}