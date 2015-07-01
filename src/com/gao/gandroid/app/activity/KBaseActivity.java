package com.gao.gandroid.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gao.gandroid.GApplication;
import com.gao.gandroid.core.AppAction;

/**
 * Activity抽象基类
 *
 * @version 1.0 创建时间：15/6/26
 */
public abstract class KBaseActivity extends FragmentActivity {
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public GApplication application;
    // 核心层的Action实例
    public AppAction appAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        application = (GApplication) this.getApplication();
        appAction = application.getAppAction();
    }
}
