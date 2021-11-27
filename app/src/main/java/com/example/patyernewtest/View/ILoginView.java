package com.example.patyernewtest.View;

public interface ILoginView {
    void onLoginSuccess(String message);
    void onLoginError(String message);
    void isCheckLogin(boolean isLogin);
}
