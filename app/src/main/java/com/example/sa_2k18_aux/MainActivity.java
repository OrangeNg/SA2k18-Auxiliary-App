package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private boolean fridgeOpen = false;

    private Button fridgeDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fridgeDoor = findViewById(R.id.fridge_door);

        dbRef.child("fridge_open").setValue(false);
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

    public void onFridgeDoorClicked(View view){
        if(fridgeOpen){
            dbRef.child("fridge_open").setValue("false");
            fridgeOpen = false;
            fridgeDoor.setText("Open Fridge");
            fridgeDoor.setTextColor(Color.parseColor("#000000"));
            fridgeDoor.setBackgroundColor(Color.parseColor("#BF76FF03"));
        }
        else{
            dbRef.child("fridge_open").setValue("true");
            fridgeOpen = true;
            fridgeDoor.setText("Close Fridge");
            fridgeDoor.setTextColor(Color.parseColor("#FFFFFF"));
            fridgeDoor.setBackgroundColor(Color.parseColor("#BFD50000"));
        }
    }

    public void onMedicationClicked(View view){
        Intent intent = new Intent(this, MedicationActivity.class);
        startActivity(intent);
    }

    public void onDoctorPanelClicked(View view){
        Intent intent = new Intent(this, DoctorPanelActivity.class);
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
