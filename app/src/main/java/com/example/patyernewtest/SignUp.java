package com.example.patyernewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patyernewtest.Presenter.LoginPresenter;
import com.example.patyernewtest.View.ILoginView;

public class SignUp extends AppCompatActivity implements ILoginView{
    EditText editEmailSignUp;
    EditText editPasswordSignUp;
    Button buttonSingUp;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editEmailSignUp = findViewById(R.id.editEmailSignUp);
        editPasswordSignUp = findViewById(R.id.editPasswordSignUp);
        buttonSingUp = findViewById(R.id.buttonSingUp);
        loginPresenter = new LoginPresenter((ILoginView) this);

        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.onSignUp(editEmailSignUp.getText().toString(), editPasswordSignUp.getText().toString());
            }
        });
    }

    @Override
    public void onLoginResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}