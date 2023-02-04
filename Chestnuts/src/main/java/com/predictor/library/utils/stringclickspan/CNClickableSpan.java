package com.predictor.library.utils.stringclickspan;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.View;


public class CNClickableSpan extends android.text.style.ClickableSpan {
    private Context context;
    private String id;
    private String content;

    public CNClickableSpan(Context context, String Id, String content) {
        this.context = context;
        this.id = Id;
        this.content = content;
    }

    public void onClick(View widget) {
        //TODO 跳转方法 跳转逻辑
//        MainActivity.start(context, id);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#00ccdd"));
        ds.setUnderlineText(false);
    }

    public static void setClickableSpan(Context context, SpannableStringBuilder builder, String appendData, String clickId, int begin, int len) {
        builder.append(appendData);
        int end = builder.length();
        int length = appendData.length();
        builder.setSpan(new CNClickableSpan(context, clickId, appendData), end - length + begin, end - length + begin + len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}