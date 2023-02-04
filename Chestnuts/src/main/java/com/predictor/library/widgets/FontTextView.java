package com.predictor.library.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.predictor.library.R;
import com.predictor.library.utils.CNNumUtils;

import java.io.InputStream;

/**
 * 基类，统一设置TextView的字体
 * app:font_name="@string/din_bold"
 * app:has_use="true"
 */
@SuppressLint("AppCompatCustomView")
public class FontTextView extends TextView {
    int beforeSize;
    int afterSize;
    private Context context;

    public FontTextView(Context context) {
        super(context);
        init(context, null);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setTextValue(String value) {
        this.setText(CNNumUtils.setNumberSize(context, value, beforeSize, afterSize));
    }


    public void setTextValue(Context mcontext, String value) {
        this.setText(CNNumUtils.setNumberSize(mcontext, value, beforeSize, afterSize));
    }



    private void init(Context context, AttributeSet attrs) {
        if (null == context) return;
        this.context = context;
        String fontPath = "";
        boolean hasUse = false;
        boolean has_Relaxation = false;
        if (null != attrs) {
            TypedArray typedArray = null;
            try {
                typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
                has_Relaxation = typedArray.getBoolean(R.styleable.FontTextView_has_Relaxation, false);
                if (has_Relaxation) {
                    String text = this.getText().toString();
                    beforeSize = typedArray.getDimensionPixelSize(R.styleable.FontTextView_font_size_before, 10);
                    afterSize = typedArray.getDimensionPixelSize(R.styleable.FontTextView_font_size_after, 10);
                    this.setText(CNNumUtils.setNumberSize(context, text, beforeSize, afterSize));
                }
                hasUse = typedArray.getBoolean(R.styleable.FontTextView_has_use, false);
                String fontName = typedArray.getString(R.styleable.FontTextView_font_name);
                if (fontName == null)
                    fontName = "DIN-Medium";
                fontPath = "fonts/" + fontName + ".otf";
                if (judgeFontWhetherExist(context, fontPath) && hasUse) {
                    this.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
                    return;
                }

            } finally {
                if (null != typedArray) {
                    typedArray.recycle();
                }
            }
        }
        fontPath = "fonts/" + "DIN-Medium" + ".otf";
        if (judgeFontWhetherExist(context, fontPath) && hasUse) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
        }
    }

    public void setmTypeface(String font_name) {
        switch (font_name) {
            case "bold":
                String fontPath = "fonts/" + font_name + ".otf";
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
                break;
            case "DIN-Medium":
                fontPath = "fonts/" + font_name + ".otf";
                this.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
                break;
        }
        invalidate();
    }

    private boolean judgeFontWhetherExist(Context context, String fontPath) {
        AssetManager assetManager = context.getAssets();
        boolean exist = false;
        try {
            InputStream inputStream = assetManager.open(fontPath);
            exist = true;
            inputStream.close();
        } catch (Exception e) {
        }
        return exist;
    }
}
