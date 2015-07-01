package com.gao.gandroid.core;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.gao.gandroid.api.Api;
import com.gao.gandroid.api.ApiImpl;
import com.gao.gandroid.api.ApiResponse;
import com.gao.gandroid.model.CouponBO;

/**
 *
 * AppAction接口的实现类
 *
 * @version 1.0 创建时间：15/6/25
 */
public class AppActionImpl implements AppAction {

    private final static int LOGIN_OS = 1; // 表示Android
    private final static int PAGE_SIZE = 20; // 默认每页20条

    private Context context;
    private Api api;

    public AppActionImpl(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    @Override
    public void sendSmsCode(final String phoneNum, final ActionCallbackListener<Void> listener) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "手机号不正确");
            }
            return;
        }

        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.sendSmsCode4Register(phoneNum);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void register(final String phoneNum, final String code, final String password, final ActionCallbackListener<Void> listener) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return;
        }
        if (TextUtils.isEmpty(code)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "验证码为空");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "手机号不正确");
            }
            return;
        }

        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.registerByPhone(phoneNum, code, password);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void login(final String loginName, final String password, final ActionCallbackListener<Void> listener) {
        // 参数检查
        if (TextUtils.isEmpty(loginName)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "登录名为空");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            }
            return;
        }

        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();
                return api.loginByApp(loginName, password, imei, LOGIN_OS);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void listCoupon(final int currentPage, final ActionCallbackListener<List<CouponBO>> listener) {
        // 参数检查
        if (currentPage < 0) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "当前页数小于零");
            }
        }

        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<CouponBO>>>() {
            @Override
            protected ApiResponse<List<CouponBO>> doInBackground(Void... voids) {
                return api.listNewCoupon(currentPage, PAGE_SIZE);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<CouponBO>> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getObjList());
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

}
