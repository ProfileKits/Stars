package com.predictor.library.net.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.predictor.library.net.lib.annotation.NetworkStateChanged;
import com.predictor.library.net.lib.annotation.OnNetworkTypeChangedTo;
import com.predictor.library.net.lib.annotation.constant.NetworkTypeEnum;

/**
 */
public final class CNetStateWatcher {
    private List<WeakReference<Object>> mObservers = new ArrayList<>();
    private static Context sContext;
    private static CNetStateWatcher sWatcher;
    private BroadcastReceiver mReceiver;
    private Intent mReceiverIntent;

    public static CNetStateWatcher getInstance() {
        if (sWatcher == null) {
            synchronized (CNetStateWatcher.class) {
                sWatcher = new CNetStateWatcher();
            }
        }
        return sWatcher;
    }

    /**
     * 初始化
     *
     * @param context application上下文对象
     */
    public void getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!");
        }
        sContext = context.getApplicationContext();
    }

    private void registerReceiverIfNeed() {
        if (sContext == null) {
            throw new IllegalStateException("Please invoke init() function in your application first.");
        }
        if (isRegisterReceiver()) {
            return;
        }
        mReceiver = new CNetStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiverIntent = sContext.getApplicationContext().registerReceiver(mReceiver, filter);
    }

    /**
     * 注册网络监听
     *
     * @param observer 观察者
     */
    public void register(Object observer) {
        if (mObservers == null) {
            synchronized (CNetStateWatcher.class) {
                mObservers = new ArrayList<>();
            }
        }
        if (observer == null) {
            return;
        }
        registerReceiverIfNeed();
        mObservers.add(new WeakReference<>(observer));
    }

    private boolean isRegisterReceiver() {
        return mReceiverIntent != null;
    }

    /**
     * 注销观察者
     *
     * @param observer 观察者
     */
    public void unRegister(Object observer) {
        if (mObservers == null || mObservers.isEmpty()) {
            return;
        }
        if (observer == null) {
            return;
        }
        mObservers.remove(observer);
    }

    /**
     * 通知观察者网络变化
     */
    void post() {
        if (mObservers == null || mObservers.isEmpty()) {
            return;
        }
        for (WeakReference<Object> observerRef : mObservers) {
            Object observer = observerRef.get();
            if (observer != null) {
                notifyObserver(observer);
            }
        }
    }

    private void notifyObserver(Object observer) {
        Class<?> clazz = observer.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            try {
                notifyNetworkStateChanged(observer, method);
                notifyNetworkTypeChangeTo(observer, method);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyNetworkTypeChangeTo(Object observer, Method method) throws InvocationTargetException, IllegalAccessException {
        OnNetworkTypeChangedTo stateAnnotation = method.getAnnotation(OnNetworkTypeChangedTo.class);
        if (stateAnnotation != null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                boolean invoke = stateAnnotation.notifyOnAppStart();
                if (!invoke) {
                    Counter.notifyTypeChangedToCount++;
                    if (Counter.notifyTypeChangedToCount > 1) {
                        invoke = true;
                    }
                }
                if (!invoke) {
                    return;
                }

                int type = stateAnnotation.type();
                int networkType = NetworkUtils.getNetworkType(sContext);
                if (type == NetworkTypeEnum.NETWORK_MOBILE) {
                    if (NetworkUtils.isMobile(sContext)) {
                        method.invoke(observer);
                    }
                } else {
                    if (type == networkType) {
                        method.invoke(observer);
                    }
                }

            }
        }
    }

    private void notifyNetworkStateChanged(Object observer, Method method) throws InvocationTargetException, IllegalAccessException {
        NetworkStateChanged stateAnnotation = method.getAnnotation(NetworkStateChanged.class);
        if (stateAnnotation != null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1) {
                Class parameterClass = parameterTypes[0];
                if (parameterClass == Integer.TYPE) {
                    boolean invoke = stateAnnotation.notifyOnAppStart();
                    if (!invoke) {
                        Counter.notifyStateChangedCount++;
                        if (Counter.notifyStateChangedCount > 1) {
                            invoke = true;
                        }
                    }
                    if (invoke) {
                        method.invoke(observer, NetworkUtils.getNetworkType(sContext));
                    }
                }
            }
        }
    }

    /**
     * 清理观察者并注销网络监听广播
     */
    public void stopWatch() {
        if (mObservers != null) {
            mObservers.clear();
            mObservers = null;
        }
        unRegisterReceiver();
        resetCounter();
    }

    private void resetCounter() {
        Counter.notifyStateChangedCount = 0;
        Counter.notifyTypeChangedToCount = 0;
    }

    /**
     * 注销广播接收者
     */
    private void unRegisterReceiver() {
        if (sContext != null) {
            sContext.unregisterReceiver(mReceiver);
            mReceiverIntent = null;
        }
    }
}
