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
    public AtomicInteger a = new AtomicInteger();
    public static  int a2;

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
        int z;
        boolean q;

        //return !TextUtils.isEmpty(getEmail()) && getPassword().length() > 8;
        if (getEmail().isEmpty() && getPassword().isEmpty()){
            a.set(2);
            a2 = 2;
            z = 2;

            Log.d("ERROR", String.valueOf(a2));
        }else{
            mAuth.signInWithEmailAndPassword(getEmail(), getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                a.set(1);
                                a2 = 1;
                                Log.d("ERROR", String.valueOf(task.isSuccessful()));
                                Log.d("ERROR", String.valueOf(a2));


                            }else{
                                a.set(-1);
                                a2 = -1;
                                Log.d("ERROR", String.valueOf(a2));
                            }

                        }
                    });
        }
        isValidData = a2;
        Log.d("ERROR", String.valueOf(a2));
        Log.d("ERROR", String.valueOf(isValidData));
        return isValidData;
    }

    @Override
    public int isSignUp() {

        if (getEmail().isEmpty() && getPassword().isEmpty()){
            //Toast.makeText(SignUp.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            a.set(2);
            Log.d("WWW", String.valueOf(a));
        }else{
            mAuth.createUserWithEmailAndPassword(getEmail(), getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                a.set(1);
                                ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(getEmail());
                                ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(getPassword());

                            }else{
                                a.set(-1);
                                //Toast.makeText(SignUp.this, "You have some errors", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("WWW", String.valueOf(a));
                        }
                    });
        }


        isValidData = a.get();
        Log.d("ERROR", String.valueOf(a));
        Log.d("ERROR", "isValidData = " + String.valueOf(isValidData));
        return isValidData;
    }

}
