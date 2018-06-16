package com.example.sa_2k18_aux;

import android.app.AlertDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFoodActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private EditText addFoodET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        addFoodET = findViewById(R.id.add_food);
    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onAddClicked(View view){
        String food = addFoodET.getText().toString().trim();

        if(food.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No food written!")
                    .setTitle("Empty field");
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        else{
            DatabaseReference foodRef = dbRef.child("food").push();
            foodRef.setValue(food);

            addFoodET.setText("");
        }
    }

}
