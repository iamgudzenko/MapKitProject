package com.example.patyernewtest.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.patyernewtest.Model.User;
import com.example.patyernewtest.View.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class LoginPresenter implements ILoginPresenter{
    private FirebaseAuth mAuth;
    ILoginView loginView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    public LoginPresenter(ILoginView loginView, FirebaseAuth auth) {
        this.mAuth = auth;
        this.loginView = loginView;
    }
    


    @Override
    public void onLogin(String email, String password) {
        User user = new User(email, password);
        if (user.getEmail().isEmpty() && user.getPassword().isEmpty()){
            loginView.onLoginError("You have some errors");
        }else {
            mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loginView.onLoginSuccess("Login success", user.getEmail());
                            } else {
                                loginView.onLoginError("Fields cannot be empty");
                            }

                        }

                    });
        }

    }


    @Override
    public void onSignUp(String email, String password) {
        User user = new User(email, password);
        if (user.getEmail().isEmpty() && user.getPassword().isEmpty()){
            loginView.onLoginError("You have some errors");
        }else{
            Task<AuthResult> task =
                    mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        loginView.onLoginSuccess("Login success", user.getEmail());
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(user.getEmail());
                                        ref.child("Users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(user.getPassword());

                                    }else{
                                        loginView.onLoginError("Fields cannot be empty");
                                    }

                                }
                            });
        }


    }

    @Override
    public void checkLogin() {
        if (mAuth.getCurrentUser() != null)
            loginView.isCheckLogin(true, mAuth.getCurrentUser().getEmail());
        else
            loginView.isCheckLogin(false, "null");
    }

}
