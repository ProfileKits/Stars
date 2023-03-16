package com.predictor.library.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import com.gyf.immersionbar.ImmersionBar;
import com.predictor.library.jni.ChestnutData;
import com.predictor.library.utils.CNLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class CNBaseActivity extends AppCompatActivity {
    protected Context mContext;
    public Typeface typeface;
    public ImmersionBar immersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        immersionBar = ImmersionBar.with(this);
        if (setFullScreen()) { //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        if(ChestnutData.getPermission()){
            setContentView(getLayoutResID());
            mContext = this;

            initView();
            initData();
            initListener();
        }
    }




    public void setStatusBar(float alpha, boolean isStatusBarDarkFont, boolean supportActionBar,
                                 boolean autoDarkModeEnable, boolean fitsSystemWindows, View barView,
                                 int statusBarColor, int statusBarColorTransform,
                                 int navigationBarColor, int navigationBarColorTransform) {
        immersionBar.statusBarDarkFont(isStatusBarDarkFont)
                .statusBarColor(statusBarColor)
                .statusBarAlpha(alpha)
                .statusBarView(barView)
                .supportActionBar(supportActionBar) //支持ActionBar使用
                .statusBarColorTransform(statusBarColorTransform)
                .navigationBarColor(navigationBarColor)
                .navigationBarColorTransform(navigationBarColorTransform)
                .autoDarkModeEnable(autoDarkModeEnable)
                .fitsSystemWindows(fitsSystemWindows)//false是全屏 true是显示任务栏
                .init();
    }


    //判断是否有权限
    public boolean hasPermission(String permission) {
        boolean has = true;
        try {
            has = PermissionChecker.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return has;
    }


    //隐藏系统菜单栏
    public void hideSysBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            uiOptions |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
    }

    protected abstract int getLayoutResID();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    protected abstract boolean setFullScreen();

    protected String getAssetText(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * View的动画效果
     *
     * @param hideView
     * @param showView
     */
    public void ViewAnimation(final View hideView, final View showView) {
        ObjectAnimator mShowAction = ObjectAnimator.ofFloat(hideView, "alpha", 1f, 0.1f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(mShowAction);
        animSet.setDuration(200);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (hideView != null) {
                    hideView.setVisibility(View.GONE);
                }
                if (showView != null) {
                    showView.setVisibility(View.VISIBLE);
                }
                ObjectAnimator mHiddenAction = ObjectAnimator.ofFloat(showView, "alpha", 0.1f, 1f, 1f);
                AnimatorSet animSetHide = new AnimatorSet();
                animSetHide.play(mHiddenAction);
                animSetHide.setDuration(200);
                animSetHide.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animSet.start();

    }

    /**
     * 不传数据 不传返回值
     *
     * @param targetActivityClass
     */
    protected void startActivity(Class<?> targetActivityClass) {
        startActivity(targetActivityClass, null, false);
    }

    /**
     * 传是否finish数据 不传返回值
     *
     * @param targetActivityClass
     */
    protected void startActivity(Class<?> targetActivityClass, boolean isFinish) {
        startActivity(targetActivityClass, null, isFinish);
    }

    /**
     * 传数据 不传返回值
     *
     * @param targetActivityClass
     * @param bundle
     */
    protected void startActivity(Class<?> targetActivityClass, Bundle bundle, boolean isFinish) {
        startActivity(targetActivityClass, bundle, 0, isFinish);
    }

    /**
     * 不传数据 传返回值 activity 不能finish
     *
     * @param targetActiviyClass
     * @param requestCode
     */
    protected void startToActivity(Class<?> targetActiviyClass, int requestCode) {
        startActivity(targetActiviyClass, null, requestCode, false);
    }

    /**
     * 传数据 传返回值 Activity 不能finish
     *
     * @param targetActiviyClass
     * @param bundle
     * @param requestCode
     */
    protected void startActivity(Class<?> targetActiviyClass, Bundle bundle, int requestCode, boolean isFinish) {
        Intent startIntent = new Intent(mContext, targetActiviyClass);
        startActivity(startIntent, bundle, requestCode, isFinish);
    }

    /**
     * 控制Activity的跳转方法
     *
     * @param startIntent intent
     * @param bundle      bundle数据
     * @param requestCode 请求码
     */
    private void startActivity(Intent startIntent, Bundle bundle, int requestCode, boolean isFinish) {
        if (startIntent != null) {
            if (bundle != null) {
                startIntent.putExtras(bundle);
            }
            if (requestCode != -1) {
                startActivity(startIntent);
                if (isFinish) {
                    finish();
                }
            } else {
                startActivityForResult(startIntent, requestCode);
            }
        }
    }


    /**
     * 功能描述：带数据的Activity之间的跳转
     *
     * @param activity
     * @param cls
     * @param hashMap
     */
    public static void startActivity(Activity activity, Class<? extends Activity> cls,
                                           HashMap<String, ? extends Object> hashMap) {
        Intent intent = new Intent(activity, cls);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                    .next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }
        }
        activity.startActivity(intent);
    }
}
