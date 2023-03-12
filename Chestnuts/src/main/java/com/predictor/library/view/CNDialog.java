package com.predictor.library.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.predictor.library.R;
import com.predictor.library.bean.CNDialogInfo;
import com.predictor.library.interfaces.DialogCallBack;
import com.predictor.library.utils.CNEditTextUtil;
import com.predictor.library.utils.CNScreenUtil;


public class CNDialog {
    public static void show(Context mContext, int layout, CNDialogInfo info, DialogCallBack callBack) {
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        View view = View.inflate(mContext, (layout == 0 || layout < 0) ? R.layout.dialog_view : layout, null);
        LinearLayout ll_bg_dialog = view.findViewById(R.id.ll_bg_dialog);
        TextView mTitle = (TextView) view.findViewById(R.id.title);
        TextView mMessage = (TextView) view.findViewById(R.id.message);
        TextView mCancel = (TextView) view.findViewById(R.id.cancel);
        TextView mConfirm = (TextView) view.findViewById(R.id.confirm);
        ImageView mClose = view.findViewById(R.id.iv_close);

        if(!info.isShowCloseBtn()){
            mClose.setVisibility(View.GONE);
        }

        if (info.getTitle() != null && !info.getTitle().isEmpty()) {
            mTitle.setText(info.getTitle());
        }
        if (info.getContent() != null && !info.getContent().isEmpty()) {
            mMessage.setText(info.getContent());
        }
        if (info.getCancelButton() != null && !info.getCancelButton().isEmpty()) {
            mCancel.setText(info.getCancelButton());
        }
        if (info.getOkButton() != null && !info.getOkButton().isEmpty()) {
            mConfirm.setText(info.getOkButton());
        }

        if(!info.isHideContent()){
            mMessage.setVisibility(View.VISIBLE);
        }else{
            mMessage.setVisibility(View.GONE);
        }

        if (info.isDarkTheme()) {
            if(info.isShowCloseBtn()){
                mClose.setImageResource(R.drawable.ic_close_night);
                mClose.setVisibility(View.VISIBLE);
            }
            mTitle.setTextColor(mContext.getResources().getColor(R.color.white));
            mMessage.setTextColor(mContext.getResources().getColor(R.color.white));
            mCancel.setTextColor(mContext.getResources().getColor(R.color.white));
            ll_bg_dialog.setBackground(mContext.getResources().getDrawable(R.drawable.bg_dark));
            mCancel.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_cancle_dark));
        } else {
            if(info.isShowCloseBtn()){
                mClose.setImageResource(R.drawable.ic_close_light);
                mClose.setVisibility(View.VISIBLE);
            }
            mTitle.setTextColor(mContext.getResources().getColor(R.color.pickerview_wheelview_textcolor_center));
            mMessage.setTextColor(mContext.getResources().getColor(R.color.pickerview_wheelview_textcolor_center));
            mCancel.setTextColor(mContext.getResources().getColor(R.color.pickerview_wheelview_textcolor_center));
            ll_bg_dialog.setBackground(mContext.getResources().getDrawable(R.drawable.bg_white));
            mCancel.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_cancle));
        }
        if (info.getOkButtonColor() != 0) {//getResources().getColor(R.color.green)
            Drawable drawable = CNEditTextUtil.tintDrawable(mContext, R.drawable.bg_btn_ok, ColorStateList.valueOf(info.getOkButtonColor()));
            mConfirm.setBackground(drawable);
        } else {
            mConfirm.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_ok));
        }

        dialog.setContentView(view);
        // 使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(info.isOutsideTouch());

        // 设置对话框的大小
        if (info.getMinHeight() > 0) {
            view.setMinimumHeight((int) (CNScreenUtil.getScreenHeight(mContext) * info.getMinHeight() > 1 ? 1 : info.getMinHeight()));
        } else {
            view.setMinimumHeight((int) (CNScreenUtil.getScreenHeight(mContext) * 0.23f));
        }
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (info.getWidth() > 0) {
            lp.width = (int) (CNScreenUtil.getScreenWidth(mContext) * info.getWidth() > 1 ? 1 : info.getWidth());
        } else {
            lp.width = (int) (CNScreenUtil.getScreenWidth(mContext) * 0.8f);
        }
        if (info.getHeight() > 0) {
            lp.height = info.getHeight() < WindowManager.LayoutParams.WRAP_CONTENT ? WindowManager.LayoutParams.WRAP_CONTENT : (int) info.getHeight();
        } else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        mCancel.setOnClickListener(v -> {
            callBack.onClick(false, dialog);
            if (!info.isClickNoDismiss()) {
                dialog.dismiss();
            }
        });
        mConfirm.setOnClickListener(v -> {
            callBack.onClick(true, dialog);
            if (!info.isClickNoDismiss()) {
                dialog.dismiss();
            }
        });
        mClose.setOnClickListener(v -> {
                dialog.dismiss();
        });

        dialog.show();
    }


    public static void show(Context mContext, CNDialogInfo info, DialogCallBack callBack) {
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        View view = View.inflate(mContext, R.layout.dialog_view, null);
        LinearLayout ll_bg_dialog = view.findViewById(R.id.ll_bg_dialog);
        TextView mTitle = (TextView) view.findViewById(R.id.title);
        TextView mMessage = (TextView) view.findViewById(R.id.message);
        TextView mCancel = (TextView) view.findViewById(R.id.cancel);
        TextView mConfirm = (TextView) view.findViewById(R.id.confirm);
        ImageView mClose = view.findViewById(R.id.iv_close);

        if(!info.isShowCloseBtn()){
            mClose.setVisibility(View.GONE);
        }

        if (info.getTitle() != null && !info.getTitle().isEmpty()) {
            mTitle.setText(info.getTitle());
        }
        if (info.getContent() != null && !info.getContent().isEmpty()) {
            mMessage.setText(info.getContent());
        }
        if (info.getCancelButton() != null && !info.getCancelButton().isEmpty()) {
            mCancel.setText(info.getCancelButton());
        }
        if (info.getOkButton() != null && !info.getOkButton().isEmpty()) {
            mConfirm.setText(info.getOkButton());
        }

        if(!info.isHideContent()){
            mMessage.setVisibility(View.VISIBLE);
        }else{
            mMessage.setVisibility(View.GONE);
        }

        if (info.isDarkTheme()) {
            if(info.isShowCloseBtn()){
                mClose.setImageResource(R.drawable.ic_close_night);
                mClose.setVisibility(View.VISIBLE);
            }
            mTitle.setTextColor(mContext.getResources().getColor(R.color.white));
            mMessage.setTextColor(mContext.getResources().getColor(R.color.white));
            mCancel.setTextColor(mContext.getResources().getColor(R.color.white));
            ll_bg_dialog.setBackground(mContext.getResources().getDrawable(R.drawable.bg_dark));
            mCancel.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_cancle_dark));
        } else {
            if(info.isShowCloseBtn()){
                mClose.setImageResource(R.drawable.ic_close_light);
                mClose.setVisibility(View.VISIBLE);
            }
            mTitle.setTextColor(mContext.getResources().getColor(R.color.pickerview_wheelview_textcolor_center));
            mMessage.setTextColor(mContext.getResources().getColor(R.color.pickerview_wheelview_textcolor_center));
            mCancel.setTextColor(mContext.getResources().getColor(R.color.pickerview_wheelview_textcolor_center));
            ll_bg_dialog.setBackground(mContext.getResources().getDrawable(R.drawable.bg_white));
            mCancel.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_cancle));
        }
        if (info.getOkButtonColor() != 0) {//getResources().getColor(R.color.green)
            Drawable drawable = CNEditTextUtil.tintDrawable(mContext, R.drawable.bg_btn_ok, ColorStateList.valueOf(info.getOkButtonColor()));
            mConfirm.setBackground(drawable);
        } else {
            mConfirm.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_ok));
        }

        dialog.setContentView(view);
        // 使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(info.isOutsideTouch());

        // 设置对话框的大小
        if (info.getMinHeight() > 0) {
            view.setMinimumHeight((int) (CNScreenUtil.getScreenHeight(mContext) * info.getMinHeight() > 1 ? 1 : info.getMinHeight()));
        } else {
            view.setMinimumHeight((int) (CNScreenUtil.getScreenHeight(mContext) * 0.23f));
        }
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (info.getWidth() > 0) {
            lp.width = (int) (CNScreenUtil.getScreenWidth(mContext) * info.getWidth() > 1 ? 1 : info.getWidth());
        } else {
            lp.width = (int) (CNScreenUtil.getScreenWidth(mContext) * 0.8f);
        }
        if (info.getHeight() > 0) {
            lp.height = info.getHeight() < WindowManager.LayoutParams.WRAP_CONTENT ? WindowManager.LayoutParams.WRAP_CONTENT : (int) info.getHeight();
        } else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        mCancel.setOnClickListener(v -> {
            callBack.onClick(false, dialog);
            if (!info.isClickNoDismiss()) {
                dialog.dismiss();
            }
        });
        mConfirm.setOnClickListener(v -> {
            callBack.onClick(true, dialog);
            if (!info.isClickNoDismiss()) {
                dialog.dismiss();
            }
        });
        mClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}
