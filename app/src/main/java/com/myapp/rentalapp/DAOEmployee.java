package com.myapp.rentalapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOEmployee {

    private DatabaseReference databaseReference;
    public DAOEmployee(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://bike-rental-app-4c471-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = db.getReference(Employee.class.getSimpleName());
    }
    public Task<Void> add(Employee emp){
        return databaseReference.push().setValue(emp);
    }
}
