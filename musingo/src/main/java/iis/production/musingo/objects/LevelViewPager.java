package iis.production.musingo.objects;

/**
 * Created by AGalkin on 2/13/14.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import iis.production.musingo.main.LevelSelectionActivity;

public class LevelViewPager extends ViewPager {

    public LevelViewPager(Context context) {
        super(context);
    }

    public LevelViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return LevelSelectionActivity.clickable;
    }
}