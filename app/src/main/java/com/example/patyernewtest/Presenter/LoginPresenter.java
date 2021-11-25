package com.example.patyernewtest.Presenter;

import com.example.patyernewtest.Model.User;
import com.example.patyernewtest.View.ILoginView;

public class LoginPresenter implements ILoginPresenter{
    ILoginView loginView;

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void onLogin(String email, String password) {
        User user = new User(email, password);
        int isLoginSuccess = user.isSignIn();

        if(isLoginSuccess == 1)
            loginView.onLoginResult("Login success");
        else if(isLoginSuccess == 2)
            loginView.onLoginResult("Fields cannot be empty");
        else if(isLoginSuccess == -1)
            loginView.onLoginResult("You have some errors");


    }

    @Override
    public void onSignUp(String email, String password) {
        User user = new User(email, password);
        int isSignUpSuccess = user.isSignUp();

        if(isSignUpSuccess == 1)
            loginView.onLoginResult("Sign Up success");
        else if(isSignUpSuccess == 2)
            loginView.onLoginResult("Sign Up Fields cannot be empty");
        else if (isSignUpSuccess == -1)
            loginView.onLoginResult("You have some errors Sign Up");
    }

}
