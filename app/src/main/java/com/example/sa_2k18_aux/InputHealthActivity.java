package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputHealthActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private EditText bodyTempET;
    private EditText pulseRateET;
    private EditText systolicBloodPressureET;
    private EditText diastolicBloodPressureET;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_health);

        bodyTempET = findViewById(R.id.body_temp);
        pulseRateET = findViewById(R.id.pulse_rate);
        systolicBloodPressureET = findViewById(R.id.systolic_blood_pressure);
        diastolicBloodPressureET =findViewById(R.id.diastolic_blood_pressure);
    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onInputClicked(View view){

        String bodyTemp = bodyTempET.getText().toString().trim();
        String pulseRate = pulseRateET.getText().toString().trim();
        String systolicBloodPressure = systolicBloodPressureET.getText().toString().trim();
        String diastolicBloodPressure = diastolicBloodPressureET.getText().toString().trim();

        if(checkValidity(bodyTemp, pulseRate, systolicBloodPressure, diastolicBloodPressure)){
            float bodyTempFloat = Float.parseFloat(bodyTemp);
            int pulseRateInt = Integer.parseInt(pulseRate);
            float systolicBloodPressureFloat = Float.parseFloat(systolicBloodPressure);
            float diastolicBloodPressureFloat = Float.parseFloat(diastolicBloodPressure);

            DatabaseReference healthInfoRef = dbRef.child("health_info");
            DatabaseReference infoRef = healthInfoRef.push();
            infoRef.child("body_temp").setValue(bodyTempFloat);
            infoRef.child("pulse_rate").setValue(pulseRateInt);
            infoRef.child("sys_blood_pressure").setValue(systolicBloodPressureFloat);
            infoRef.child("dias_blood_pressure").setValue(diastolicBloodPressureFloat);

            Date currentTime = Calendar.getInstance().getTime();
            String timeStamp = sdf.format(currentTime);
            infoRef.child("timestamp").setValue(timeStamp);


            bodyTempET.setText("");
            pulseRateET.setText("");
            systolicBloodPressureET.setText("");
            diastolicBloodPressureET.setText("");
        }
    }

    private boolean checkValidity(String bodyTemp, String pulseRate, String systolicBloodPressure, String diastolicBloodPressure){
        try {
            float bodyTempFloat = Float.parseFloat(bodyTemp);
            if(bodyTempFloat < 35.0 || bodyTempFloat > 41.0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter a valid body temperature!")
                        .setTitle("Temperature invalid");
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
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
            if(pulseRateFloat < 0 || pulseRateFloat > 220){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter a valid pulse rate!")
                        .setTitle("Pulse rate invalid");
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
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
            float systolicBloodPressureFloat = Float.parseFloat(systolicBloodPressure);
            if(systolicBloodPressureFloat < 60 || systolicBloodPressureFloat > 200){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter a valid systolic blood pressure!")
                        .setTitle("Blood Pressure invalid");
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }

        }
        catch(NumberFormatException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a valid systolic blood pressure!")
                    .setTitle("Number format error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        try {
            float diastolicBloodPressureFloat = Float.parseFloat(diastolicBloodPressure);
            if(diastolicBloodPressureFloat < 40 || diastolicBloodPressureFloat > 180){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter a valid diastolic blood pressure!")
                        .setTitle("Blood Pressure invalid");
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }

        }
        catch(NumberFormatException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a valid diastolic blood pressure!")
                    .setTitle("Number format error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        float sysBloodPressure = Float.parseFloat(systolicBloodPressure);
        float diasBloodPressure = Float.parseFloat(diastolicBloodPressure);
        if (sysBloodPressure <= diasBloodPressure){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Systolic blood pressure has to be higher than diastolic blood pressure!")
                    .setTitle("Blood pressures invalid");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        return true;

    }
}
