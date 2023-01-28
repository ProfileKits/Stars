package com.predictor.galaxy;

import android.graphics.Color;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.predictor.library.base.CNBaseActivity;

import com.predictor.library.base.CNBaseInvoke;
import com.predictor.library.utils.CNDES;
import com.predictor.library.utils.CNLogUtil;
import com.predictor.library.view.CNTextTool;
import com.predictor.view.PickerView;
import com.predictor.view.SelectTimeCallBack;


public class MainActivity extends CNBaseActivity {
    TextView textView,textView2;
    private PickerView pickerView;

    @Override
    protected void initView() {
        textView = findViewById(R.id.tv);
        textView2 = findViewById(R.id.tv2);

        pickerView = new PickerView(this, new SelectTimeCallBack() {
            @Override
            public void onTimeSelect(String time) {
                textView.setText(time);
            }
        });

        pickerView.pvOptions.setTitle("选择预约时间");

        //点击弹出选项选择器
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pickerView.pvOptions.show();
            }
        });
    }

    private void testDES() {
        String key = "1fgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34tgerteegry44g45tge34t";
        try {
            textView.setText(CNDES.encryptDES("Hello World",key));
            textView2.setText(CNDES.decryptDES(textView.getText().toString(),key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pickerView.pvOptions.isShowing()){
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
        CNBaseInvoke.getInstance().init(this);

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

    }



    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }


    @Override
    protected boolean setFullScreen() {
        setStatusBar(0,true,false,false,true
                ,null,R.color.white,R.color.white,R.color.white,R.color.white);
        return true;
    }
}