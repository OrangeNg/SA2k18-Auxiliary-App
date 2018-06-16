package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAddFoodClicked(View view){
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }

    public void onMovePastClicked(View view){
        dbRef.child("move_past").setValue(true);
    }

    public void onRemoveFoodClicked(View view){
        Intent intent = new Intent(this, RemoveFoodActivity.class);
        startActivity(intent);
    }

    public void onInputHealthClicked(View view){
        Intent intent = new Intent(this,InputHealthActivity.class);
        startActivity(intent);
    }

    public void onOpenFridgeClicked(View view){
        dbRef.child("open_fridge").setValue(true);
    }

    public void onRemoveMedicationClicked(View view){
        dbRef.child("remove_medication").setValue(true);
    }

    public void onDoctorCommentsClicked(View view){
        Intent intent = new Intent(this, DoctorCommentsActivity.class);
        startActivity(intent);
    }

    public void onHelpClicked(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Click on the buttons to simulate signals sent to smart fridge!")
                .setTitle("Help");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
