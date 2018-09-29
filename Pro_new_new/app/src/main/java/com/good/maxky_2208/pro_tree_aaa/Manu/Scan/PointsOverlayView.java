package com.good.maxky_2208.pro_tree_aaa.Manu.Scan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Maxky_2208 on 8/5/2560.
 */

public class PointsOverlayView extends View {

    PointF[] points;
    private Paint paint;

    public PointsOverlayView(Context context) {
        super(context);
        init();
    }

    public PointsOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointsOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setPoints(PointF[] points) {
        this.points = points;
        invalidate();
    }

    @Override public void draw(Canvas canvas) {
        super.draw(canvas);
        if (points != null) {
            for (PointF pointF : points) {
                canvas.drawCircle(pointF.x, pointF.y, 10, paint);
            }
        }
    }

}
