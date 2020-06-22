package com.example.login.contract;

import com.example.login.presenter.LoginPresenter;

public interface LoginContract {

    //处理数据
    interface Model {
        void login(String name, String psw, LoginPresenter loginPresenter);
    }

    //显示界面
    interface View {
        // 登录失败
        void onFail(String msg);
        // 登录成功
        void onSuccess();
        // 获取账号
        String getName();
        // 获取密码
        String getPassWord();
    }

    //逻辑处理
    interface Presenter {
        // 登陆成功
        void onSuccess();
        // 登陆失败
        void onFail(String msg);
    }
}
