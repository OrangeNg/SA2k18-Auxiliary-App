package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorCommentsActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private EditText doctorCommentsET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_comments);
        doctorCommentsET = findViewById(R.id.doctor_comments);
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
            dbRef.child("doctor_comments").setValue(doctorComments);
        }

    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
