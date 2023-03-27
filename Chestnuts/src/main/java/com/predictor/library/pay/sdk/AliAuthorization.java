package com.predictor.library.pay.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.AuthTask;
import com.predictor.library.R;
import com.predictor.library.pay.sdk.utils.AuthResult;
import com.predictor.library.pay.sdk.utils.OrderInfoUtil2_0;

import java.util.Map;

public class AliAuthorization {

    /**
     * 获取支付宝账户授权功能
     */
    public static String getAuthV2(Activity context, AliBean aliBean, AuthorizationCallback callback) {
        if (TextUtils.isEmpty(aliBean.getPID()) || TextUtils.isEmpty(aliBean.getAPPID())
                || (TextUtils.isEmpty(aliBean.getRSA2_PRIVATE()) && TextUtils.isEmpty(aliBean.getRSA_PRIVATE()))
                || TextUtils.isEmpty(aliBean.getTARGET_ID())) {
            return "参数错误: 需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID";
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (aliBean.getRSA2_PRIVATE().length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(aliBean.getPID(), aliBean.getAPPID(), aliBean.getTARGET_ID(), rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? aliBean.getRSA2_PRIVATE() : aliBean.getRSA_PRIVATE();
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(context);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                AuthResult authResult = new AuthResult(result, true);
                String resultStatus = authResult.getResultStatus();

                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    Info info = new Info("授权成功", authResult);
                    callback.callback(info);
                } else {
                    // 其他状态值则为授权失败
                    Info info = new Info("授权失败", authResult);
                    callback.callback(info);
                }
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
        return "参数正常";
    }

}
