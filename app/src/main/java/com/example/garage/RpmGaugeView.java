package com.example.garage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class RpmGaugeView extends View {
    private Paint trackPaint;
    private Paint progressPaint;
    private RectF rectF;

    // Cài đặt thông số RPM
    private float maxRpm = 12000f;
    private float currentRpm = 0f;

    public RpmGaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Cọ vẽ nền xám (track)
        trackPaint = new Paint();
        trackPaint.setColor(Color.parseColor("#333333"));
        trackPaint.setStyle(Paint.Style.STROKE);
        trackPaint.setStrokeWidth(25f);
        trackPaint.setStrokeCap(Paint.Cap.ROUND);
        trackPaint.setAntiAlias(true); // Chống răng cưa giúp viền mịn

        // Cọ vẽ mức RPM hiện tại (màu cam)
        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#FF8C00")); // Màu cam
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(25f);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setAntiAlias(true);

        rectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padding = 30f;
        rectF.set(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vẽ viền xám
        canvas.drawArc(rectF, 135f, 270f, false, trackPaint);

        // Vẽ viền cam
        float sweepAngle = (currentRpm / maxRpm) * 270f;
        canvas.drawArc(rectF, 135f, sweepAngle, false, progressPaint);
    }

    //  MainActivity gọi vào khi muốn đổi số RPM
    public void setRpm(float rpm) {
        if (rpm > maxRpm) rpm = maxRpm;
        if (rpm < 0) rpm = 0;
        this.currentRpm = rpm;
        invalidate(); 
    }
}
