package com.example.login.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.contract.LoginContract;
import com.example.login.presenter.LoginPresenter;
import com.example.login.secondActivity;

public class LoginActivity extends MainActivity implements LoginContract.View, View.OnClickListener {
    private EditText loginName;
    private EditText loginPwd;
    private Button button;
    private LoginPresenter loginPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginName = findViewById(R.id.userNameInput);
        loginPwd = findViewById(R.id.pwdInput);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        loginPresenter = new LoginPresenter(this);
        Log.i("初始化", "initial");

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button){
            loginPresenter.login();
        }
    }

    @Override
    public void onFail(final String msg) {
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("result", "fail");
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(this, secondActivity.class);
        startActivity(intent);
        Log.i("result", "success");
    }

    @Override
    public String getName() {
        return loginName.getText().toString();
    }

    @Override
    public String getPassWord() {
        return loginPwd.getText().toString();
    }
}
