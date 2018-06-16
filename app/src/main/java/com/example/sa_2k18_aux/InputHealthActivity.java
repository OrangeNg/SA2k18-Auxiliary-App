package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputHealthActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private EditText bodyTempET;
    private EditText pulseRateET;
    private EditText bloodPressureET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_health);

        bodyTempET = findViewById(R.id.body_temp);
        pulseRateET = findViewById(R.id.pulse_rate);
        bloodPressureET = findViewById(R.id.blood_pressure);
    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onInputClicked(View view){

        String bodyTemp = bodyTempET.getText().toString().trim();
        bodyTempET.setText("");
        String pulseRate = pulseRateET.getText().toString().trim();
        pulseRateET.setText("");
        String bloodPressure = bloodPressureET.getText().toString().trim();
        bloodPressureET.setText("");

        if(checkValidity(bodyTemp, pulseRate, bloodPressure)){
            float bodyTempFloat = Float.parseFloat(bodyTemp);
            int pulseRateInt = Integer.parseInt(pulseRate);
            float bloodPressureFloat = Float.parseFloat(bloodPressure);

            DatabaseReference healthInfoRef = dbRef.child("health_info");
            healthInfoRef.child("body_temp").setValue(bodyTempFloat);
            healthInfoRef.child("pulse_rate").setValue(pulseRateInt);
            healthInfoRef.child("blood_pressure").setValue(bloodPressureFloat);
        }
    }

    private boolean checkValidity(String bodyTemp, String pulseRate, String bloodPressure){
        try {
            float bodyTempFloat = Float.parseFloat(bodyTemp);
        }
        catch(NumberFormatException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a valid body temperature!")
                    .setTitle("Number format error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        try {
            int pulseRateFloat = Integer.parseInt(pulseRate);
        }
        catch(NumberFormatException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a valid pulse rate!")
                    .setTitle("Number format error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        try {
            float bloodPressureFloat = Float.parseFloat(bloodPressure);
        }
        catch(NumberFormatException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a valid blood pressure!")
                    .setTitle("Number format error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        return true;

    }
}
