package com.predictor.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.predictor.library.R;

public class CNDialog {
    //显示加载中动画
    public static Dialog showLoading(Activity context){
        if (context == null || context.isFinishing() || context.isDestroyed()) {
            return null;
        }
        //Dialog dialog = new Dialog(context,R.style.dialog_style);
        LayoutInflater inflater = LayoutInflater.from(context);
        //加载loading_dialog.xml
        View v = inflater.inflate(R.layout.layout_dialog_loading, null);// 得到加载view

        // loading_dialog.xml中的LinearLayout
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        // 加载动画load_animation.xml
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.load_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(anim);

        // 创建自定义样式loading_dialog
        Dialog loadingDialog = new Dialog(context, R.style.dialog_style);
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        // 设置布局
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
        loadingDialog.show();
        return loadingDialog;
    }


    /**
     * 弹出单按钮的弹窗
     * @return
     */
    public static Dialog showDialog(Context context, String msg, View.OnClickListener confirmCallback){
        Dialog dialog = new Dialog(context,R.style.dialog_style);
        dialog.setContentView(R.layout.layout_dialog_single_button);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        TextView msgTv =dialog.findViewById(R.id.tv_msg);
        msgTv.setText(msg);
        View confirm = dialog.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(confirmCallback != null){
                    confirmCallback.onClick(v);
                }
            }
        });
        dialog.show();
        return dialog;
    }
}
