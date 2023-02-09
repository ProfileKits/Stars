package com.predictor.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.reflect.Field;

//禁止输入框复制粘贴
public class CNTextViewUtil {
    /**
     * 改变TextView的字体颜色
     */
    public static void textColor(final TextView textView, boolean isHintColor, int color) {
        if (color != 0) {
            textView.setTextColor(color);
        } else {
            if (isHintColor) {
                textView.setTextColor(Color.parseColor("#8f8f8f"));
            } else {
                textView.setTextColor(Color.parseColor("#303030"));
            }
        }
    }

    /**
     * 给TextView控件设置左边Icon图标
     * @param context
     * @param drawableRes R.drawable.xxx
     * @param textView TextView object
     * @param drawableColor Color.parseColor("#000000")  等于零原色彩
     */
    public static void setTextLeftIcon(Context context,int drawableRes,final TextView textView,int drawableColor) {
        Drawable iconDrawable = context.getResources().getDrawable(drawableRes);
        if(drawableColor!=0) {
            Drawable drawable = CNEditTextUtil.tintDrawable(iconDrawable, ColorStateList.valueOf(drawableColor));
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        }else{
            iconDrawable.setBounds(0, 0, iconDrawable.getMinimumWidth(), iconDrawable.getMinimumHeight());
            textView.setCompoundDrawables(iconDrawable, null, null, null);
        }
    }


    public interface LinkClickListener {
        void onLinkClick(Object object);
    }


    /**
     * @param textView 目标TextView
     * @param moreStr   more型字符串，当显示不完全的时候显示替代字符串
     * @param clickListener 点击的回调接口
     */
    public static void getTextMaxEms(final TextView textView, final String moreStr,  final LinkClickListener clickListener){
        final String contentStr=textView.getText().toString();
        ViewTreeObserver viewTreeObserver=textView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                if(textView.getTag()==null){
                    textView.setTag(textView.getText().toString());
                }
                String currentStr=textView.getText().toString();
                ViewTreeObserver treeObserver=textView.getViewTreeObserver();
                treeObserver.removeOnGlobalLayoutListener(this);
                int lineCount=textView.getLineCount();
                if(lineCount>1) {
                    //获取第一行的文本长度当做每行文本的长度
                    int lineLength = textView.getText().subSequence(textView.getLayout().getLineStart(0), textView.getLayout().getLineEnd(0)).toString().length();

                    //获取最后一行文本的长度
                    int lastLineLength = textView.getText().subSequence(textView.getLayout().getLineStart(textView.getLayout().getLineCount() - 1), textView.getLayout().getLineEnd(textView.getLayout().getLineCount() - 1)).toString().length();

                    if (lastLineLength >= lineLength - moreStr.length() - 2) {
                        currentStr = currentStr.substring(0, contentStr.length() - (lastLineLength - (lineLength - moreStr.length() - 5))) + "...";
                    }
                    final String finalStr = currentStr + moreStr;
                    SpannableString spanString = new SpannableString(finalStr);
                    ClickableSpan clickSpan = new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            clickListener.onLinkClick(contentStr);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setColor(ds.linkColor);
                            ds.setUnderlineText(true);
                        }
                    };
                    spanString.setSpan(clickSpan, currentStr.length(), finalStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(spanString);
                    textView.setLinkTextColor(Color.RED);
                    //必须添加这一段
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setFocusable(false);
                    textView.setClickable(false);
                    textView.setLongClickable(false);
                }
            }
        });
    }



    /**
     * 设置textView结尾...后面显示的文字和颜色
     * @param context 上下文
     * @param textView textview
     * @param minLines 最少的行数
     * @param originText 原文本
     * @param endText 结尾文字
     * @param endColorID 结尾文字颜色id
     * @param isExpand 当前是否是展开状态
     */
    public static void toggleEllipsize(final Context context,
                                       final TextView textView,
                                       final int minLines,
                                       final String originText,
                                       final String endText,
                                       final int endColorID,
                                       final boolean isExpand) {
        if (TextUtils.isEmpty(originText)) {
            return;
        }
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isExpand) {
                    CharSequence temp = originText + endText;
                    SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
                    ssb.setSpan(new ForegroundColorSpan(context.getResources().getColor
                                    (endColorID)),
                            temp.length() - endText.length(), temp.length(),    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    textView.setText(ssb);
                } else {
                    int paddingLeft = textView.getPaddingLeft();
                    int paddingRight = textView.getPaddingRight();
                    TextPaint paint = textView.getPaint();
                    float moreText = textView.getTextSize() * endText.length();
                    float availableTextWidth = (textView.getWidth() - paddingLeft - paddingRight) *
                            minLines - moreText;
                    CharSequence ellipsizeStr = TextUtils.ellipsize(originText, paint,
                            availableTextWidth, TextUtils.TruncateAt.END);
                    if (ellipsizeStr.length() < originText.length()) {
                        CharSequence temp = ellipsizeStr + endText;
                        SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
                        ssb.setSpan(new ForegroundColorSpan(context.getResources().getColor
                                        (endColorID)),
                                temp.length() - endText.length(), temp.length(),    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        textView.setText(ssb);
                    } else {
                        textView.setText(originText);
                    }
                }
                if (Build.VERSION.SDK_INT >= 16) {
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

}