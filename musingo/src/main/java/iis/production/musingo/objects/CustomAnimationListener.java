package iis.production.musingo.objects;

import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

/**
 * Created by dima on 3/3/14.
 */
public class CustomAnimationListener implements Animation.AnimationListener {
    private LinearLayout linearLayout;

    public CustomAnimationListener(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        linearLayout.setVisibility(View.GONE);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

}