package com.example.login.presenter;

import android.util.Log;
import com.example.login.contract.LoginContract;
import com.example.login.model.LoginModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.collection.ArraySet;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginModel loginModel;

    public LoginPresenter(LoginContract.View view){
        this.view = view;
        loginModel = new LoginModel();
    }

    @Override
    public void onSuccess() {
        view.onSuccess();
    }

    @Override
    public void onFail(String msg) {
        view.onFail(msg);
    }

    public void login(){
        //从view层获取参数
        String name = view.getName();
        String pwd = view.getPassWord();


        //对数据做判断，调用api来检测登陆是否成功
        if(name.isEmpty() || pwd.isEmpty()){
            this.onFail("账号密码不能为空");
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder formBody = new FormBody.Builder();
                    formBody.add("username", view.getName());
                    formBody.add("password", view.getPassWord());
                    Request request = new Request.Builder().url("http://php7.foodhwy.net/api/login").post(formBody.build()).build();
                    try{
                        Response response = client.newCall(request).execute();
                        if(response.isSuccessful()){
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Object status =  jsonObject.get("status");
                            Log.d("status", status.toString());

                            Log.d("登陆结果", responseData);
                            if((int) status == 1) {
                                view.onSuccess();
                            }else {
                                view.onFail("用户名密码错误");
                            }
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void parseJsonWithJsonObject(Response response) throws IOException {
        String responseData = response.body().string();
        try{
            JSONArray jsonArray=new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                ArraySet<String> idList = new ArraySet<>();
                idList.add(id);
                ArraySet<String> nameList = new ArraySet<>();
                nameList.add(name);
                Log.i("key", idList.toString());
                Log.i("value", nameList.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
