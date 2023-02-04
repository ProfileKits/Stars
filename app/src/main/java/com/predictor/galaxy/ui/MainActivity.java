package com.predictor.galaxy.ui;

import android.animation.Animator;
import android.graphics.Color;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.predictor.galaxy.R;
import com.predictor.library.artanimation.library.CNDoooArt;
import com.predictor.library.artanimation.library.Techniques;
import com.predictor.library.base.CNBaseActivity;

import com.predictor.library.example.SetSelfWithPoint;
import com.predictor.library.listener.OnChangeListener;
import com.predictor.library.pickerview.interfaces.SelectTimeCallBack;
import com.predictor.library.utils.CNAnimationUtils;
import com.predictor.library.utils.CNBugly;
import com.predictor.library.utils.CNDES;
import com.predictor.library.utils.CNEditTextUtil;
import com.predictor.library.utils.CNLogUtil;
import com.predictor.library.utils.CNTextViewUtil;
import com.predictor.library.utils.CNToastCustom;
import com.predictor.library.view.CNCleanEditText;
import com.predictor.library.view.CNTextTool;
import com.predictor.galaxy.view.PickerView;


public class MainActivity extends CNBaseActivity {
    private TextView textView, textView2;
    private PickerView pickerView;
    private Button btn_viewpage;
    private CNDoooArt.YoYoString rope;
    private CNCleanEditText et;

    @Override
    protected void initView() {
        textView = findViewById(R.id.tv);
        textView2 = findViewById(R.id.tv2);
        btn_viewpage = findViewById(R.id.btn_viewpage);
        et = findViewById(R.id.et);


        CNEditTextUtil.setChangeListener(et, new OnChangeListener() {
            @Override
            public void change() {
                CNBugly.testCrash();
//                CNToast.show(MainActivity.this,et.getText().toString());
//                CNCustomToast.ToastLongTopCenter(MainActivity.this,et.getText().toString());
//                CNToastCustom.showWhiteToast(MainActivity.this,"测试");
//                CNToastCustom.setCustom(MainActivity.this,et.getText().toString(),100,100,10,255,28,false);
                CNToastCustom.showBlackToast(MainActivity.this,et.getText().toString());
            }
        });
        CNTextViewUtil.setTextLeftIcon(this,R.drawable.kaixin,textView2,0);
        CNLogUtil.i("key22222222222:");
    }

    private void testDES() {
        String key = "1fgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34t";
        try {
            textView.setText(CNDES.encryptDES("Hello World", key));
            textView2.setText(CNDES.decryptDES(textView.getText().toString(), key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pickerView != null && pickerView.pvOptions.isShowing()) {
                pickerView.dissmiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void initData() {
        //打印日志
        CNLogUtil.i("Hello World");
        testDES();
        //测试拼装字符串
        CNTextTool.getBuilder("拼装字符串").setContext(this).setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                .append(" ")
                .append("绿色的").setTypefaceHNDB().setForegroundColor(Color.parseColor("#008800")).setBold()
                .append(" ")
                .append("灰色的").setTypefaceHND().setForegroundColor(Color.parseColor("#67222222")).setNoBold()
                .append(" ")
                .append("蓝色的").setTypefaceHNDB().setForegroundColor(Color.parseColor("#777888")).setBold()
                .append(" ")
                .append("红色的").setTypefaceHND().setForegroundColor(Color.parseColor("#ee3313")).setNoBold()
                .into(textView);

//        CNToast.show(this,"Hello World");

//        CNSnackbar.show(this,"Hello World",textView);

        //自定义Toast
//        CNCustomToast.ToastLongCenter(MainActivity.this, "自定义Toast");

        //旋转动画
        CNAnimationUtils.startWith(textView, CNAnimationUtils.IN_ROTATE360, 1000);

    }


    private void testAnimation() {
        SetSelfWithPoint.with(true).repeat(200).setTimes(1000);

        if (rope != null) {
            rope.stop(true);
        }
        rope = CNDoooArt.with((Techniques.ZoomInRight))
                .duration(1200)
                .repeat(CNDoooArt.INFINITE)
                .pivot(CNDoooArt.CENTER_PIVOT, CNDoooArt.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(MainActivity.this, "canceled previous animation", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(textView2);
    }


    @Override
    protected void initListener() {
        btn_viewpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ViewPagerActivity.class);
            }
        });
        //点击弹出选项选择器
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择时间选择器
                pickerView = new PickerView(MainActivity.this, "选择预约时间", new SelectTimeCallBack() {
                    @Override
                    public void onTimeSelect(String time) {
                        textView.setText(time);
                    }

                    @Override
                    public void onCancel() {
                        CNAnimationUtils.stopWith(textView);
                    }
                });

            }
        });
        textView.invalidate();
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAnimation();
            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }


    @Override
    protected boolean setFullScreen() {
        setStatusBar(0, true, false, false, true
                , null, R.color.white, R.color.white, R.color.white, R.color.white);
        return true;
    }
}