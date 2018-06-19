package com.example.sa_2k18_aux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemoveFoodActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private ListView foodLV;
    private ArrayAdapter<String> foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_food);
        foodLV = findViewById(R.id.food_list);

        dbRef.child("food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });

    }

    private void populateList(DataSnapshot dataSnapshot){
        ArrayList<String> foods = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.getChildren()){
            foods.add(child.getValue().toString());
        }

        foodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
        foodLV.setAdapter(foodAdapter);

        AdapterView.OnItemClickListener foodItemClick = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                TextView tv = (TextView) v;
                final String food = tv.getText().toString();
                dbRef.child("food").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            if(child.getValue().equals(food)){
                                dbRef.child("food").child(child.getKey()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Do nothing
                    }
                });
                foodAdapter.remove(food);
            }
        };

        foodLV.setOnItemClickListener(foodItemClick);

    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
