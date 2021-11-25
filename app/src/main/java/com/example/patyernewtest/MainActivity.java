package com.example.patyernewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patyernewtest.Presenter.ILoginPresenter;
import com.example.patyernewtest.Presenter.LoginPresenter;
import com.example.patyernewtest.R;
import com.example.patyernewtest.View.ILoginView;

public class MainActivity extends AppCompatActivity implements ILoginView {
    EditText editEmail;
    EditText editPassword;
    Button buttonLogin;
    Button buttonSingUpActivity;

    ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSingUpActivity = findViewById(R.id.buttonSingUpActivity);

        loginPresenter = new LoginPresenter(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.onLogin(editEmail.getText().toString(), editPassword.getText().toString());
            }
        });

        buttonSingUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onLoginResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}