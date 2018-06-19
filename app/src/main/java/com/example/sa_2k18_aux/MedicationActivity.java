package com.example.sa_2k18_aux;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicationActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private TimePicker timePicker;
    private EditText medNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        timePicker = findViewById(R.id.med_time);
        medNameET = findViewById(R.id.med_name);
    }

    public void onAddTimeClicked(View view){
        DatabaseReference timeRef = dbRef.child("medication").child("time").push();

        int hour = 0;
        int min = 0;

        if(Build.VERSION.SDK_INT < 23){
            hour = timePicker.getCurrentHour();
            min = timePicker.getCurrentMinute();

        }
        else{
            hour = timePicker.getHour();
            min = timePicker.getMinute();
        }
        timeRef.setValue("" + hour + ":" + min);
    }

    public void onEnterMedicationClicked(View view){
        String medName = medNameET.getText().toString().trim();
        dbRef.child("medication").child("name").setValue(medName);
    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
