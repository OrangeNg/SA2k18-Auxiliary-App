package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorPanelActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private EditText doctorCommentsET;
    private EditText recommendedCalorieET;
    private TextView apmtInfoTV;
    private LinearLayout confirmButtonsLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_panel);
        doctorCommentsET = findViewById(R.id.doctor_comments);
        recommendedCalorieET = findViewById(R.id.daily_calorie);
        apmtInfoTV = findViewById(R.id.apmt_info);
        confirmButtonsLL = findViewById(R.id.confirm_buttons);

        DatabaseReference doctorRef = dbRef.child("doctor_panel");
        doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setupUI(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do nothing
            }
        });
    }

    private void setupUI(DataSnapshot dataSnapshot){
        if(dataSnapshot.hasChild("appointment")){
            apmtInfoTV.setText(dataSnapshot.child("appointment").child("request").getValue().toString());
            if(dataSnapshot.child("appointment").child("status").getValue().equals("Processing")){
                confirmButtonsLL.setVisibility(View.VISIBLE);
            }
        }
        else{
            apmtInfoTV.setText("No appointment requests");
        }
    }

    public void onEnterCommentsClicked(View view){
        String doctorComments = doctorCommentsET.getText().toString().trim();

        if(doctorComments.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No comment written!")
                    .setTitle("Empty field");
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        else{
            dbRef.child("doctor_panel").child("doctor_comments").setValue(doctorComments);
        }

    }

    public void onEnterCaloriesClicked(View view){
        String recCalories = recommendedCalorieET.getText().toString().trim();

        try{
            int recCalorieInt = Integer.parseInt(recCalories);
            if(recCalorieInt < 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter a valid calorie value!")
                        .setTitle("Number format error");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                dbRef.child("doctor_panel").child("daily_calorie").setValue(recCalorieInt);
            }
        }
        catch(NumberFormatException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a valid calorie value!")
                    .setTitle("Number format error");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onAcceptClicked(View view){
        dbRef.child("doctor_panel").child("appointment").child("status").setValue("Accepted");
        confirmButtonsLL.setVisibility(View.GONE);
    }

    public void onDeclineClicked(View view){
        dbRef.child("doctor_panel").child("appointment").child("status").setValue("Declined");
        confirmButtonsLL.setVisibility(View.GONE);
    }
}
