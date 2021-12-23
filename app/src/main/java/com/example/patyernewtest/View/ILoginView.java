package com.example.patyernewtest.View;

import java.util.ArrayList;

public interface ILoginView {
    void onLoginSuccess(String message, String emailUser);
    void onLoginError(String message);
    void isCheckLogin(boolean isLogin, String emailUser);
}
