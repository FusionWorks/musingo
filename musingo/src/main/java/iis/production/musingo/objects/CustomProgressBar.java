package iis.production.musingo.objects;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by dima on 2/19/14.
 */
public class CustomProgressBar extends Drawable {
    private static final int NUM_RECTS = 9;
    Paint mPaint = new Paint();

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    public CustomProgressBar(){}

    @Override
    public void draw(Canvas canvas) {
        int level = getLevel();
        Rect b = getBounds();
        float width = b.width();
        for (int i = 0; i < NUM_RECTS; i++) {
            float left = width * i / NUM_RECTS;
            float right = left + 0.9f * width / NUM_RECTS;
            mPaint.setColor((i + 1) * 10000 / NUM_RECTS <= level? 0xff3fb8cd : 0xffb2ced4);
            RectF rect = new RectF(left, b.top, right, b.bottom);
            canvas.drawRoundRect(rect, 10, 10, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}