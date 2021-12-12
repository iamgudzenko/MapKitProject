package com.example.patyernewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.joda.time.DateTime;

import java.util.Date;

public class AddNewPlaceMark extends AppCompatActivity implements AddPlaceMark {
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    double latitude;
    double longitude;
    TextInputEditText editPlace;
    TextInputEditText name;
    TextInputEditText des;
    TextInputEditText contact;
    TextInputEditText timeStartTysa;
    TextInputEditText removeInHoursEdit;
    TextView textError;
    String email;
    Button createPlace;
    PlaceMarkPresenter placeMarkPresenter;
    AutoCompleteTextView mAutoCompleteTextView;
    final String[] spinerrText = { "Выбрать", "Моё местоположение", "Выбрать точку на карте"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place_mark);
        Bundle arguments = getIntent().getExtras();

        if(arguments.getBoolean("isPoint")){
            latitude = arguments.getDouble("latitude");
            longitude = arguments.getDouble("longitude");
            spinerrText[0] = "Точка на карте";

        }

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinerrText);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);

                if(item.equals(spinerrText[1])){
                    latitude = arguments.getDouble("userLatitude");
                    longitude = arguments.getDouble("userLongitude");

                } else if (item.equals(spinerrText[2])){
                    //Переход на выбор точки
                    Intent intent = new Intent (AddNewPlaceMark.this, PointSelectionMap.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);


        name = findViewById(R.id.editNameMark);
        des = findViewById(R.id.editDes);
        contact = findViewById(R.id.editContact);
        createPlace = findViewById(R.id.buttonCreatePlace);
        timeStartTysa = findViewById(R.id.timeStartTysa);
        removeInHoursEdit = findViewById(R.id.removeInHoursEdit);
        textError = findViewById(R.id.textError);
        mAuth = FirebaseAuth.getInstance();
        placeMarkPresenter = new PlaceMarkPresenter(this);


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

                if(!name.getText().toString().isEmpty() && !des.getText().toString().isEmpty() && !contact.getText().toString().isEmpty() && !timeStartTysa.getText().toString().isEmpty() && !removeInHoursEdit.getText().toString().isEmpty()){

                    if (timeStartTysa.getText().toString().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")){
                        int removeInHours;
                        try
                        {
                            removeInHours =  Integer.parseInt(removeInHoursEdit.getText().toString().trim());
                            if(removeInHours <= 24 && removeInHours > 0) {
                                DateTime dataTime = new DateTime(new Date());
                                String dataString = dataTime.toString("yyyy-MM-dd") + "T" +  dataTime.toString("HH:mm");
                                placeMarkPresenter.writePlaceMarkToDB(name.getText().toString(), latitude, longitude, email, des.getText().toString(), contact.getText().toString(), dataString, timeStartTysa.getText().toString(), removeInHours);

                            } else {
                                removeInHoursEdit.setError("от 1 до 24 ч");
                            }

                        }
                        catch (NumberFormatException nfe)
                        {
                            removeInHoursEdit.setError("цифры от 1 до 24");
                        }

                        } else {

                        timeStartTysa.setError("неккоректное время");

                    }
                } else {
                    textError.setText("не оставляйте поля пустыми");
                }


            }
        });

    }

    @Override
    public void writePlaceMarkDone(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }


}