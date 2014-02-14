package iis.production.musingo.objects;

/**
 * Created by AGalkin on 2/13/14.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LevelViewPager extends android.support.v4.view.ViewPager {
    private boolean enabled;

    public LevelViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    /**
     * Custom implementation to enable or not swipe :)
     *
     * @param enabled
     *            true to enable swipe, false otherwise.
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}