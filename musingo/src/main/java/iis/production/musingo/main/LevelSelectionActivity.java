package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.async.ATSongs;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.DidYouKnow;
import iis.production.musingo.utility.Endpoints;
import iis.production.musingo.utility.OnSwipeTouchListener;
import iis.production.musingo.utility.Reachability;
import iis.production.musingo.utility.RoundedCornersDrawable;

/**
 * Created by AGalkin on 1/18/14.
 */
public class LevelSelectionActivity extends Activity{
    // views
    RelativeLayout dropDown;
    ImageView arrow;
    ArrayList<ImageView> levelViews;
    LinearLayout levels;
    ArrayList<RelativeLayout> songViews;
    RelativeLayout loadingAnimation;
    TextViewArchitects didyouknowText;
    TextViewPacifico starNumber;

    ImageView level1;
    ImageView level2;
    ImageView level3;
    ImageView level4;
    ImageView level5;
    ImageView level6;
    ImageView level7;
    ImageView level8;
    ImageView level9;

    RelativeLayout song1;
    RelativeLayout song2;
    RelativeLayout song3;
    RelativeLayout song4;
    RelativeLayout song5;
    RelativeLayout song6;
    RelativeLayout song7;
    RelativeLayout song8;
    RelativeLayout song9;


    //variables
    boolean opened;
    boolean beat;
    boolean complete;
    boolean powerup;

    boolean clickable;

    static ArrayList<Song> gameSongs;
    int selectedLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        didyouknowText = (TextViewArchitects)findViewById(R.id.didyouknowText);
        DidYouKnow.random(didyouknowText, this);
        opened = false;
        levelViews = new ArrayList<ImageView>();
        gameSongs = new ArrayList<Song>();

        //Views initialize
        loadingAnimation = (RelativeLayout)findViewById(R.id.loadingAnimation);
        levels = (LinearLayout)findViewById(R.id.levels);
        starNumber = (TextViewPacifico)findViewById(R.id.starNumber);

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

        song1 = (RelativeLayout) findViewById(R.id.song1);
        song2 = (RelativeLayout) findViewById(R.id.song2);
        song3 = (RelativeLayout) findViewById(R.id.song3);
        song4 = (RelativeLayout) findViewById(R.id.song4);
        song5 = (RelativeLayout) findViewById(R.id.song5);
        song6 = (RelativeLayout) findViewById(R.id.song6);
        song7 = (RelativeLayout) findViewById(R.id.song7);
        song8 = (RelativeLayout) findViewById(R.id.song8);
        song9 = (RelativeLayout) findViewById(R.id.song9);

        songViews = new ArrayList<RelativeLayout>();
        songViews.add(song1);
        songViews.add(song2);
        songViews.add(song3);
        songViews.add(song4);
        songViews.add(song5);
        songViews.add(song6);
        songViews.add(song7);
        songViews.add(song8);
        songViews.add(song9);

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
                MusingoApp.soundButton();
                for(int i = 0; i<levelViews.size(); i++){
                    ImageView imageView = levelViews.get(i);
                    if(imageView.getTag().toString().equals("selected") && i!=levelViews.size()-1 && clickable){
                        imageView = makeUnselected(imageView);
                        ImageView imageNext = levelViews.get(i+1);
                        imageNext = makeSelected(imageNext);
                        break;
                    }
                }
            }
            public void onSwipeLeft() {
                MusingoApp.soundButton();
                for(int i = 0; i<levelViews.size(); i++){
                    ImageView imageView = levelViews.get(i);
                    if(imageView.getTag().toString().equals("selected") && i!=0 && clickable){
                        imageView = makeUnselected(imageView);
                        ImageView imageNext = levelViews.get(i-1);
                        imageNext = makeSelected(imageNext);
                        break;
                    }
                }
            }
        });

        getStarsCollected();
        selectedLevel = 1;
        ArrayList<Song> songs = new ArrayList<Song>();
        String url = Endpoints.playlist_url + selectedLevel;
        ATSongs ATS = new ATSongs(this, url, loadingAnimation, false, songs);
        Reachability a = new Reachability(this);
        if(a.isOnline()){
            ATS.execute();
        }

        clickable = false;
    }

    public void dropDown(View view){
        MusingoApp.soundButton();
        if(opened)
            hideMenu();
        else
            showMenu();
    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
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
        selectedLevel = Integer.parseInt(imageView.getTag().toString());
        imageView.setTag("selected");
        changeLevel();
        return imageView;
    }

    public ImageView makeUnselected(ImageView imageView){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (15 * scale + 0.5f);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.height = pixels;
        params.width = pixels;
        imageView.setLayoutParams(params);
        imageView.setTag(String.valueOf(selectedLevel));
        return imageView;
    }

    public void onLevelsSelectionClick(View view){
        if(clickable){
            MusingoApp.soundButton();
            ImageView selectedView = (ImageView)levels.findViewWithTag("selected");
            makeUnselected(selectedView);
            makeSelected((ImageView)view);
        }
    }

    public void goToLevel(View view){
        if(clickable){
            MusingoApp.soundButton();
            String url = Endpoints.playlist_url + selectedLevel;
            ATSongs ATS = new ATSongs(this, url, loadingAnimation, true, gameSongs);
            Reachability a = new Reachability(this);
            if(a.isOnline()){
                ATS.execute();
            }
            clickable = false;
        }
    }

    public void downloadResultForLevels(ArrayList<Song> songs, String name){
        TextViewArchitects songsTitle = (TextViewArchitects)findViewById(R.id.songsTitle);
        songsTitle.setTextColor(Color.parseColor("#9ad74c"));
        songsTitle.setText("UNLOCK: "+name);
        if (isUnlocked()){
            songsTitle.setTextColor(Color.parseColor("#ffffff"));
            songsTitle.setText("Unlocked "+name);
        }

        for (int i = 0; i<songs.size(); i++){
            Song song = songs.get(i);
            RelativeLayout view = (RelativeLayout)songViews.get(i);
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            TextViewArchitects textView = (TextViewArchitects)view.findViewById(R.id.title);

            final RoundedCornersDrawable drawable = new RoundedCornersDrawable(getResources(), song.getImage());
            imageView.setImageDrawable(drawable);

//            Utility.setBackgroundBySDK(imageView, song.getImage());
            textView.setText(song.getSongName());
        }
        gameSongs = songs;
        clickable = true;
    }

    public void downloadResultForGame(ArrayList<Song> songs, int scoreToBeat, String name, int cost){
        gameSongs = songs;
        Log.v("Musingo", "Level selection : " + selectedLevel);
        Intent intent = new Intent();
        intent.setClass(this, MainGameActivity.class);
        intent.putExtra("scoreTobeat", scoreToBeat);
        intent.putExtra("name", name);
        intent.putExtra("cost", cost);
        intent.putExtra("selectedLevel", selectedLevel);
//<--- temp
        intent.putExtra("packageNumber", 2);
        intent.putExtra("packageName", "Hits 90s");
//--->>
        startActivity(intent);
        clickable = true;
    }

    public void changeLevel(){
        ArrayList<Song> songs = new ArrayList<Song>();
        String url = Endpoints.playlist_url + selectedLevel;
        ATSongs ATS = new ATSongs(this, url, loadingAnimation, false, songs);
        Reachability a = new Reachability(this);
        if(a.isOnline()){
            ATS.execute();
        }
        clickable = false;
    }

    public boolean isUnlocked(){
        if(selectedLevel == 0)
            return true;
        else
            return false;
    }

    public void getStarsCollected(){
        PlaySongsTable PST = new PlaySongsTable(this);
        starNumber.setText(String.valueOf(PST.getSumStars()));
    }
}