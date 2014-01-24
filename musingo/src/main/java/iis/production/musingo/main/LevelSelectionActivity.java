package iis.production.musingo.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.objects.OnSwipeTouchListener;

/**
 * Created by AGalkin on 1/18/14.
 */
public class LevelSelectionActivity extends Activity{
    // views
    RelativeLayout dropDown;
    ImageView arrow;
    ArrayList<ImageView> levelViews;
    LinearLayout levels;

    ImageView level1;
    ImageView level2;
    ImageView level3;
    ImageView level4;
    ImageView level5;
    ImageView level6;
    ImageView level7;
    ImageView level8;
    ImageView level9;

    boolean opened;
    boolean beat;
    boolean complete;
    boolean powerup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

        opened = false;
        levels = (LinearLayout)findViewById(R.id.levels);
        levelViews = new ArrayList<ImageView>();

        level1 = (ImageView)findViewById(R.id.level1);
        level2 = (ImageView)findViewById(R.id.level2);
        level3 = (ImageView)findViewById(R.id.level3);
        level4 = (ImageView)findViewById(R.id.level4);
        level5 = (ImageView)findViewById(R.id.level5);
        level6 = (ImageView)findViewById(R.id.level6);
        level7 = (ImageView)findViewById(R.id.level7);
        level8 = (ImageView)findViewById(R.id.level8);
        level9 = (ImageView)findViewById(R.id.level9);
        levelViews.add(level1);
        levelViews.add(level2);
        levelViews.add(level3);
        levelViews.add(level4);
        levelViews.add(level5);
        levelViews.add(level6);
        levelViews.add(level7);
        levelViews.add(level8);
        levelViews.add(level9);

        // temp
        beat = true;
        complete = true;
        powerup = true;
        //temp
        dropDown = (RelativeLayout)findViewById(R.id.dropDown);
        arrow = (ImageView)findViewById(R.id.arrow);
        LinearLayout purple = (LinearLayout)findViewById(R.id.purple);
        LinearLayout green = (LinearLayout)findViewById(R.id.green);
        LinearLayout orange = (LinearLayout)findViewById(R.id.orange);
        if (beat) orange.setVisibility(View.VISIBLE);
        if (complete) green.setVisibility(View.VISIBLE);
        if (powerup) purple.setVisibility(View.VISIBLE);

        levels.setOnTouchListener(new OnSwipeTouchListener(){
            public void onSwipeRight() {
                for(int i = 0; i<levelViews.size(); i++){
                    ImageView imageView = levelViews.get(i);
                    if(imageView.getTag().toString().equals("selected") && i!=levelViews.size()-1){
                        imageView = makeUnselected(imageView);
                        ImageView imageNext = levelViews.get(i+1);
                        imageNext = makeSelected(imageNext);
                        break;
                    }
                }
            }
            public void onSwipeLeft() {
                for(int i = 0; i<levelViews.size(); i++){
                    ImageView imageView = levelViews.get(i);
                    if(imageView.getTag().toString().equals("selected") && i!=0){
                        imageView = makeUnselected(imageView);
                        ImageView imageNext = levelViews.get(i-1);
                        imageNext = makeSelected(imageNext);
                        break;
                    }
                }
            }
        });
    }

    public void dropDown(View view){
        if(opened)
            hideMenu();
        else
            showMenu();
    }

    public void goBackButton(View view){
        if(opened)
           hideMenu();
        else
            finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBackButton(null);
        }
//        return super.onKeyDown(keyCode, event);
        return false;
    }

    public void showMenu(){
        opened = true;
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.drop_down);
        anim.setInterpolator((new AccelerateDecelerateInterpolator()));
        anim.setFillAfter(true);
        arrow.setImageResource(R.drawable.arrow_up);
        dropDown.startAnimation(anim);
    }
    public void hideMenu(){
        opened = false;
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.drop_up);
        anim.setInterpolator((new AccelerateDecelerateInterpolator()));
        anim.setFillAfter(true);

        arrow.setImageResource(R.drawable.arrow_down);
        dropDown.startAnimation(anim);
    }

    public ImageView makeSelected(ImageView imageView){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (20 * scale + 0.5f);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.height = pixels;
        params.width = pixels;
        imageView.setLayoutParams(params);
//        imageView.setBackgroundResource(R.drawable.level_sel);
        imageView.setTag("selected");
        return imageView;
    }

    public ImageView makeUnselected(ImageView imageView){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (15 * scale + 0.5f);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.height = pixels;
        params.width = pixels;
        imageView.setLayoutParams(params);
//        imageView.setBackgroundResource(R.drawable.level_un);
        imageView.setTag("unselected");
        return imageView;
    }

}
