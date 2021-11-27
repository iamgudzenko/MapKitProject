package com.example.patyernewtest.Presenter;

public interface ILoginPresenter {
    void onLogin(String email, String password);
    void onSignUp(String email, String password);
    void checkLogin();
}
