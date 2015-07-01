package com.gao.gandroid.core;

import java.util.List;

import com.gao.gandroid.model.CouponBO;

/**
 * 接收app层的各种Action
 *
 * @version 1.0 创建时间：15/6/25
 */
public interface AppAction {
    /**
     * 发送验证码
     *
     * @param phoneNum 手机号
     * @param listener 回调监听器
     */
    public void sendSmsCode(String phoneNum, ActionCallbackListener<Void> listener);

    /**
     * 注册
     *
     * @param phoneNum 手机号
     * @param code     验证码
     * @param password 密码
     * @param listener 回调监听器
     */
    public void register(String phoneNum, String code, String password, ActionCallbackListener<Void> listener);

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @param listener  回调监听器
     */
    public void login(String loginName, String password, ActionCallbackListener<Void> listener);

    /**
     * 券列表
     *
     * @param currentPage 当前页数
     * @param listener    回调监听器
     */
    public void listCoupon(int currentPage, ActionCallbackListener<List<CouponBO>> listener);
}
