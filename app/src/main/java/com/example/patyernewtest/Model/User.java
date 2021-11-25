package com.example.patyernewtest.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicInteger;

public class User implements com.example.patyernewtest.Model.IUser {
    private String email, password;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    int isValidData;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isSignIn() {

        mAuth = FirebaseAuth.getInstance();
        int isValidData;


        if (getEmail().isEmpty() && getPassword().isEmpty()){

            isValidData = 2;

        }else{
            Task<AuthResult> task =
            mAuth.signInWithEmailAndPassword(getEmail(), getPassword());
            if(task.isSuccessful()){
                isValidData = 1;
            } else {
                isValidData = -1;
            }
        }

        Log.d("ERROR", "isValidData = " + String.valueOf(isValidData));
        return isValidData;
    }

    @Override
    public int isSignUp() {

        if (getEmail().isEmpty() && getPassword().isEmpty()){
            isValidData = 2;
        }else{
            Task<AuthResult> task =
            mAuth.createUserWithEmailAndPassword(getEmail(), getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(getEmail());
                                ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(getPassword());

                            }else{

                            }

                        }
                    });
            if(task.isSuccessful()){
                isValidData = 1;
            } else {
                isValidData = -1;
            }
        }

        Log.d("ERROR", "isValidData = " + String.valueOf(isValidData));
        return isValidData;
    }

}
