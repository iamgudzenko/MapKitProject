package com.example.patyernewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patyernewtest.Presenter.PlaceMarkPresenter;
import com.example.patyernewtest.View.AddPlaceMark;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewPlaceMark extends AppCompatActivity implements AddPlaceMark {
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    double latitude;
    double longitude;
    TextInputEditText editPlace;
    TextInputEditText name;
    TextInputEditText des;
    TextInputEditText contact;
    TextInputEditText time;
    String email;
    Button createPlace;
    PlaceMarkPresenter placeMarkPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place_mark);

        Bundle arguments = getIntent().getExtras();
        latitude = arguments.getDouble("latitude");
        longitude = arguments.getDouble("longitude");

        name = findViewById(R.id.editNameMark);

        editPlace = findViewById(R.id.editPlace);
        des = findViewById(R.id.editDes);
        contact = findViewById(R.id.editContact);
        createPlace = findViewById(R.id.buttonCreatePlace);
        mAuth = FirebaseAuth.getInstance();
        placeMarkPresenter = new PlaceMarkPresenter(this);


        //editPlace.setText(latitude + " " + longitude);

        ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                email = snapshot.child("email").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });

        createPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeMarkPresenter.writePlaceMarkToDB(name.getText().toString(), latitude, longitude, email, des.getText().toString(), contact.getText().toString() );
            }
        });

    }

    @Override
    public void writePlaceMarkDone(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddNewPlaceMark.this, MapActivity.class);
        startActivity(intent);
        finish();
    }


}