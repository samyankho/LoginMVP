package com.example.login.model;

import android.util.Log;

import com.example.login.contract.LoginContract;
import com.example.login.presenter.LoginPresenter;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginModel implements LoginContract.Model {

    public void login(String name, String pwd, final LoginPresenter presenter){
        //对数据做判断，调用api来检测登陆是否成功
        if(name.isEmpty() || pwd.isEmpty()){
            presenter.onFail("账号密码不能为空");
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder formBody = new FormBody.Builder();
                    formBody.add("username", "6479891678");
                    formBody.add("password", "cfb326cd57");
                    Request request = new Request.Builder().url("http://php7.foodhwy.net/api/login").post(formBody.build()).build();
                    try{
                        Response response = client.newCall(request).execute();
                        if(response.isSuccessful()){
                            Log.d("登陆结果", response.body().string());
                            presenter.onSuccess();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
