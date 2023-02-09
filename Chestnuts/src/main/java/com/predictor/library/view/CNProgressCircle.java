package com.predictor.library.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.predictor.library.R;

public class CNProgressCircle extends View {
    private int maxProgress = 100;
    private float progress = 0;
    private int currentProgress = 0;
    private float progressStrokeWidth = 10;
    private float marxArcStorkeWidth = 10;
    private int progressBackgroundColor;
    private int progressCircleColor;
    private int textColor;
    private float numberSize;
    private float unitSize;
    private String unit;
    // 画圆所在的距形区域
    private RectF oval;
    private Paint paintProgressBg;
    private Paint paintProgressCircle;
    private Paint paintNumber;
    private Paint paintUnit;
    private Context context;
    private ValueAnimator progressAnimator;

    public CNProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        oval = new RectF();
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.progress_circle);
        numberSize = a.getDimension(R.styleable.progress_circle_number_text_size, getResources().getDimensionPixelSize(R.dimen.scale_textSize));
        unitSize = a.getDimension(R.styleable.progress_circle_unit_text_size, getResources().getDimensionPixelSize(R.dimen.scale_textSize));

        progressCircleColor = a.getColor(R.styleable.progress_circle_progress_circle_line_color, Color.RED);
        progressBackgroundColor = a.getColor(R.styleable.progress_circle_progress_background_line_color, Color.RED);
        textColor = a.getColor(R.styleable.progress_circle_text_color, Color.RED);

        marxArcStorkeWidth = a.getDimension(R.styleable.progress_circle_progress_circle_line_width, getResources().getDimensionPixelSize(R.dimen.scale_textSize));
        progressStrokeWidth = a.getDimension(R.styleable.progress_circle_progress_background_line_width, getResources().getDimensionPixelSize(R.dimen.scale_textSize));
        unit = a.getString(R.styleable.progress_circle_unit);
        initPanit();
        a.recycle();
    }

    private void initPanit() {
        paintProgressBg = new Paint();
        paintProgressBg.setAntiAlias(true); // 设置画笔为抗锯齿
        paintProgressBg.setColor(progressBackgroundColor); // 设置画笔颜色
        paintProgressBg.setStrokeWidth(progressStrokeWidth); // 线宽
        paintProgressBg.setStyle(Style.STROKE);

        paintProgressCircle = new Paint();
        paintProgressCircle.setAntiAlias(true); // 设置画笔为抗锯齿
        paintProgressCircle.setColor(progressCircleColor); // 设置画笔颜色
        paintProgressCircle.setStrokeWidth(marxArcStorkeWidth); // 线宽
        paintProgressCircle.setStyle(Style.STROKE);

        paintNumber = new Paint();
        paintNumber.setTextSize(numberSize);
        paintNumber.setColor(textColor);
//        String fontPath = "fonts/" + context.getString(R.string.font_textview_default_font) + ".otf";
//        paintNumber.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
        paintNumber.setStyle(Style.FILL);

        paintUnit = new Paint();
        paintUnit.setTextSize(unitSize);
        paintUnit.setColor(textColor);
//        paintUnit.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
        paintUnit.setStyle(Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

        width = (width > height) ? height : width;
        height = (width > height) ? height : width;

        canvas.drawColor(Color.TRANSPARENT); // 白色背景

        oval.left = marxArcStorkeWidth / 2; // 左上角x
        oval.top = marxArcStorkeWidth / 2; // 左上角y
        oval.right = width - marxArcStorkeWidth / 2; // 左下角x
        oval.bottom = height - marxArcStorkeWidth / 2; // 右下角y

        canvas.drawArc(oval, -90, 360, false, paintProgressBg); // 绘制白色圆圈，即进度条背景
        canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360, false, paintProgressCircle); // 绘制进度圆弧，这里是蓝色

        String text = String.valueOf((int) progress);
        Rect bounds = new Rect();
        paintNumber.getTextBounds(text, 0, text.length(), bounds);
        int unitWidth = (int) paintUnit.measureText(unit, 0, unit.length());
        float numberHeight = bounds.height();
        int progressWidth = (int) paintNumber.measureText(text, 0, text.length());
        float progressY = height / 2 + numberHeight / 2;
        float progressX = width / 2 - progressWidth / 2 - unitWidth / 2;
        canvas.drawText(text, progressX, progressY, paintNumber);

        paintUnit.getTextBounds(unit, 0, unit.length(), bounds);

        canvas.drawText(unit, progressX + progressWidth, progressY, paintUnit);
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 设置进度
     *
     * @param progress 进度百分比
     * @param duration 动画时长
     */
    public void setProgress(int progress, long duration) {
        this.progress = progress;
        setAnimator(currentProgress, progress, duration);
    }

    private void setAnimator(int last, int current, long duration) {
        progressAnimator = ValueAnimator.ofInt(last, current);
        progressAnimator.setDuration(duration);
        progressAnimator.setTarget(currentProgress);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        this.invalidate();
    }

    public float getProgress() {
        return progress;
    }

    public void cancelAnimator() {
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
    }

}