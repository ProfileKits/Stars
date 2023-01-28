package com.predictor.library.view;

import static com.predictor.library.utils.CNImageTool.dp2px;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.predictor.library.R;


/**
 例如：<CNProgressBar
 android:id="@+id/download_progress_bar"
 android:layout_width="match_parent"
 android:layout_height="2dp"
 android:layout_alignParentBottom="true"
 app:loadingColor="#32C166" />

 * 描述：添加圆角支持 on 2016/11/11
 * </br>
 */
public class CNProgressBar extends View implements Runnable {
    private int DEFAULT_HEIGHT_DP = 4;

    private int borderWidth;

    private float maxProgress = 100f;


    private Paint bgPaint;

    private Paint pgPaint;


    private RectF bgRectf;

    /**
     * 进度条 bitmap ，包含滑块
     */
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    /**
     * 当前进度
     */
    private float progress;

    private boolean isFinish;

    private boolean isStop;

    /**
     * 下载中颜色
     */
    private int loadingColor;

    /**
     * 暂停时颜色
     */
    private int pauseColor;

    /**
     * 完成时的眼神
     */
    private int finishColor;

    /**
     * 进度停下来的颜色
     */
    private int stopColor;

    /**
     * 进度文本、边框、进度条颜色
     */
    private int progressColor;

    private int radius;

    private Thread thread;

    BitmapShader bitmapShader;

    public CNProgressBar(Context context) {
        this(context, null, 0);
    }

    public CNProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CNProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FlikerProgressBar);
        try {
            loadingColor = ta.getColor(R.styleable.FlikerProgressBar_loadingColor, Color.parseColor("#000000"));
            pauseColor = ta.getColor(R.styleable.FlikerProgressBar_stopColor, Color.parseColor("#000000"));
            finishColor = ta.getColor(R.styleable.FlikerProgressBar_finishColor, Color.parseColor("#00ffffff"));
            radius = (int) ta.getDimension(R.styleable.FlikerProgressBar_radius, 0);
            borderWidth = (int) ta.getDimension(R.styleable.FlikerProgressBar_borderWidth, 1);
        } finally {
            ta.recycle();
        }
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);

        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Paint.Style.FILL);

        bgRectf = new RectF(borderWidth, borderWidth, getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth);

        if (isStop) {
            progressColor = pauseColor;
        } else {
            progressColor = loadingColor;
        }

        initPgBimap();
    }

    private void initPgBimap() {
        pgBitmap = Bitmap.createBitmap(getMeasuredWidth() - borderWidth, getMeasuredHeight() - borderWidth, Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);
        thread = new Thread(this);
        thread.start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
                height = dp2px(DEFAULT_HEIGHT_DP);
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                height = heightSpecSize;
                break;
            default:
                break;
        }
        setMeasuredDimension(widthSpecSize, height);

        if (pgBitmap == null) {
            init();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //进度
        drawProgress(canvas);


    }

    /**
     * 边框
     *
     * @param canvas
     */
    private void drawBackGround(Canvas canvas) {
        bgPaint.setColor(progressColor);
        //left、top、right、bottom不要贴着控件边，否则border只有一半绘制在控件内,导致圆角处线条显粗
        canvas.drawRoundRect(bgRectf, radius, radius, bgPaint);
    }

    /**
     * 进度
     */
    @SuppressLint("WrongConstant")
    private void drawProgress(Canvas canvas) {
        pgPaint.setColor(progressColor);

        float right = (progress / maxProgress) * getMeasuredWidth();
        pgCanvas.save();//Canvas.CLIP_SAVE_FLAG
        pgCanvas.clipRect(0, 0, right, getMeasuredHeight());
        pgCanvas.drawColor(progressColor);
        pgCanvas.restore();

        //控制显示区域
        bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        pgPaint.setShader(bitmapShader);
        canvas.drawRoundRect(bgRectf, radius, radius, pgPaint);
    }


    public void setProgress(float progress) {
        if (!isStop) {
            if (progress < maxProgress) {
                this.progress = progress;
            } else {
                this.progress = maxProgress;
                finishLoad();
            }
            invalidate();
        }
    }

    private void setFinish(boolean stop) {
        isStop = stop;
        if (isStop) {
            progressColor = finishColor;
            thread.interrupt();
        } else {
            progressColor = loadingColor;
            thread = new Thread(this);
            thread.start();
        }
        invalidate();
    }

    public void setStop(boolean stop) {
        isStop = stop;
        if (isStop) {
            progressColor = pauseColor;
            thread.interrupt();
        } else {
            progressColor = loadingColor;
            thread = new Thread(this);
            thread.start();
        }
        invalidate();
    }

    public void finishLoad() {
        isFinish = true;
        setFinish(true);
    }

    public void toggle() {
        if (!isFinish) {
            if (isStop) {
                setStop(false);
            } else {
                setStop(true);
            }
        }
    }

    @Override
    public void run() {
    }

    /**
     * 重置
     */
    public void reset() {
        setStop(true);
        progress = 0;
        isFinish = false;
        isStop = false;
        progressColor = loadingColor;
        initPgBimap();
    }

    public float getProgress() {
        return progress;
    }

    public boolean isStop() {
        return isStop;
    }

    public boolean isFinish() {
        return isFinish;
    }

}
