package ru.sberbank.materialdesignsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by User22 on 22.06.2017.
 */

public class SampleView extends View {

    private Paint mBorderPaint;
    private Paint mInnerPaint;

    private int mMinWidth;
    private int mMinHeight;

    public SampleView(Context context) {
        this(context, null);
    }

    public SampleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.Widget_SampleView);
    }

    public SampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        //получаем маппинг атрибутов к их значениям
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.SampleView, defStyleAttr, defStyleRes);
        try {
            mBorderPaint = createBorderPaint(styledAttrs);
            mInnerPaint = createInnerPaint(styledAttrs);

            mMinWidth = styledAttrs.getDimensionPixelSize(R.styleable.SampleView_android_minWidth, 0);
            mMinHeight = styledAttrs.getDimensionPixelSize(R.styleable.SampleView_android_minHeight, 0);
        } finally {
            styledAttrs.recycle();
        }
    }

    private static Paint createBorderPaint(TypedArray styledAttrs) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(styledAttrs.getColor(R.styleable.SampleView_borderColor, 0));
        paint.setStrokeWidth(styledAttrs.getDimension(R.styleable.SampleView_customBorderWidth, 1));
        return paint;
    }

    private static Paint createInnerPaint(TypedArray styledAttrs) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(styledAttrs.getColor(R.styleable.SampleView_innerColor, 0));
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredHeight = mMinWidth + getPaddingLeft() + getPaddingRight();
        int desiredWidth = mMinWidth + getPaddingTop() + getPaddingBottom();

        final int measuredHeight = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0);
        final int measuredWidth = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //сохраняем матрицу канваса
        canvas.save();

        canvas.translate(getPaddingLeft(), getPaddingRight());
        float left = 0;
        float top = 0;
        float right = getWidth() - getPaddingLeft() - getPaddingRight();
        float bottom = getHeight() - getPaddingTop() - getPaddingBottom();
        canvas.clipRect(left, top, right, bottom);
        //рисуем квадрат внутренней запоняющей краской
        canvas.drawRect(left, top, right, bottom, mInnerPaint);
        //поверх рисуем границу
        canvas.drawRect(left, top, right, bottom, mBorderPaint);

        //востанавливаем матрицу канваса
        canvas.restore();


        super.onDraw(canvas);
    }
}
