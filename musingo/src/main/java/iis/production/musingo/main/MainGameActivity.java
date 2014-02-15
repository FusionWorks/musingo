package iis.production.musingo.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.main.more.TokenShopActivity;
import iis.production.musingo.objects.AlertViewOrange;
import iis.production.musingo.objects.AlertViewPink;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.FacebookManager;
import iis.production.musingo.utility.RoundedCornersDrawable;
import iis.production.musingo.utility.SongsManager;
import iis.production.musingo.utility.Utility;

public class MainGameActivity extends Activity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

    // Views vars
    TextView tokens;
    TextViewArchitects scoreToBeat;
    TextViewArchitects yourScore;
    TextViewPacifico barTitle;
    TextViewPacifico levelNumber;
    RelativeLayout seekBar;
    ImageView bar;
    int cost = 0;
    ArrayList<RelativeLayout> songTimes;
    ArrayList<RelativeLayout> songThumbs;
    ArrayList<Song> songs;
    static ArrayList<Song> songsWithTime;
    ArrayList<Song> availableForHint;
    int currentSong;
    int skippedSong;
    int previousSongTime;
    boolean userRight;
    int score;

    RelativeLayout song1;
    RelativeLayout song2;
    RelativeLayout song3;
    RelativeLayout song4;
    RelativeLayout song5;
    RelativeLayout song6;
    RelativeLayout song7;
    RelativeLayout song8;
    RelativeLayout song9;

    RelativeLayout songThumb1;
    RelativeLayout songThumb2;
    RelativeLayout songThumb3;
    RelativeLayout songThumb4;
    RelativeLayout songThumb5;
    RelativeLayout songThumb6;
    RelativeLayout songThumb7;
    RelativeLayout songThumb8;
    RelativeLayout songThumb9;

    RelativeLayout leftView;

    ImageView hintfb;
    RelativeLayout hintHint;
    RelativeLayout hintSkip;
    RelativeLayout hintFreeze;
    RelativeLayout hintReplay;
    RelativeLayout hintLonger;
    RelativeLayout hintNextList;
    ImageView hintTextImage;
    ImageView tutorial1Arrow;
    ImageView tutorial3Arrow;
    ImageView tutorial4Arrow;
    ImageView tutorial5Arrow;

    LinearLayout tutorial1;
    LinearLayout tutorial2;
    LinearLayout tutorial3;
    LinearLayout tutorial4;
    LinearLayout tutorial5;
    LinearLayout tutorial6;

    int maxTryes = 0;
    HashMap<String, Integer> allIndexes;
    ArrayList<Integer> correctIndexes;
    SharedPreferences firstRun = null;
    SharedPreferences firstBonus = null;

    //Media Player vars
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    MediaPlayer mp;
    SongsManager songManager;
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private Handler mHandler = new Handler();
    ImageView wrongSelected;
    int level;

    int correctSongs;
    float offset;
    int packageNumber;
    String packageName;
    int neededScore;
    boolean powerUpUsed;
    boolean scoreBeaten;
    boolean allSongsCorrect;
    boolean skip;
    boolean skipBorder;
    boolean replay;
    boolean freeze;
    int maximumTime;
    int freezeTime;
    ArrayList<RelativeLayout> hintSelected;

    AlertDialog dialog;
    // Bonuses
    boolean crissCrossBonus = true;
    boolean cornersBonus = true;
    boolean diagonalsBonus = true;
    boolean squaresRowBonus = true;
    boolean squaresBonus = true;

    boolean tutorialOpen;
    boolean bonusOpen;
    boolean waitingForResultActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        allIndexes = new HashMap<String, Integer>();
        correctIndexes = new ArrayList<Integer>();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        seekBar = (RelativeLayout)findViewById(R.id.seekBar);
        bar = (ImageView)seekBar.findViewById(R.id.bar);
        barTitle = (TextViewPacifico)findViewById(R.id.barTitle);
        scoreToBeat = (TextViewArchitects)findViewById(R.id.scoreToBeat);
        yourScore = (TextViewArchitects)findViewById(R.id.yourScore);
        levelNumber = (TextViewPacifico)findViewById(R.id.levelNumber);
        hintTextImage = (ImageView)findViewById(R.id.hintTextImage);
        songs = new ArrayList<Song>();
        songsWithTime = new ArrayList<Song>();
        availableForHint = new ArrayList<Song>();
        availableForHint = LevelSelectionActivity.gameSongs;
        tokens = (TextView)findViewById(R.id.coins);
        tokens.setText(String.valueOf(mSettings.getInt("tokens",0)));
        powerUpUsed = false;
        scoreBeaten = false;
        allSongsCorrect = false;
        skip = false;
        replay = false;
        correctSongs = 0;

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpScreenWidth  = outMetrics.widthPixels / density;
        offset = (dpScreenWidth - 60.0f) / 9.0f;

        hintSelected = new ArrayList<RelativeLayout>();
        //Powerups icons
        hintfb = (ImageView)findViewById(R.id.hintfb);
        hintHint = (RelativeLayout)findViewById(R.id.hintHint);
        hintSkip = (RelativeLayout)findViewById(R.id.hintSkip);
        hintFreeze = (RelativeLayout)findViewById(R.id.hintFreeze);
        hintReplay = (RelativeLayout)findViewById(R.id.hintReplay);
        hintLonger = (RelativeLayout)findViewById(R.id.hintLonger);
        hintNextList = (RelativeLayout)findViewById(R.id.hintNextList);

        //Song Thumbs layouts
        songThumb1 = (RelativeLayout)findViewById(R.id.songThumb1);
        songThumb2 = (RelativeLayout)findViewById(R.id.songThumb2);
        songThumb3 = (RelativeLayout)findViewById(R.id.songThumb3);
        songThumb4 = (RelativeLayout)findViewById(R.id.songThumb4);
        songThumb5 = (RelativeLayout)findViewById(R.id.songThumb5);
        songThumb6 = (RelativeLayout)findViewById(R.id.songThumb6);
        songThumb7 = (RelativeLayout)findViewById(R.id.songThumb7);
        songThumb8 = (RelativeLayout)findViewById(R.id.songThumb8);
        songThumb9 = (RelativeLayout)findViewById(R.id.songThumb9);

        //Songs mini timing views
        song1 = (RelativeLayout)findViewById(R.id.song1);
        song2 = (RelativeLayout)findViewById(R.id.song2);
        song3 = (RelativeLayout)findViewById(R.id.song3);
        song4 = (RelativeLayout)findViewById(R.id.song4);
        song5 = (RelativeLayout)findViewById(R.id.song5);
        song6 = (RelativeLayout)findViewById(R.id.song6);
        song7 = (RelativeLayout)findViewById(R.id.song7);
        song8 = (RelativeLayout)findViewById(R.id.song8);
        song9 = (RelativeLayout)findViewById(R.id.song9);

        songThumbs = new ArrayList<RelativeLayout>();
        songThumbs.add(songThumb1);
        songThumbs.add(songThumb2);
        songThumbs.add(songThumb3);
        songThumbs.add(songThumb4);
        songThumbs.add(songThumb5);
        songThumbs.add(songThumb6);
        songThumbs.add(songThumb7);
        songThumbs.add(songThumb8);
        songThumbs.add(songThumb9);

        songTimes = new ArrayList<RelativeLayout>();
        songTimes.add(song1);
        songTimes.add(song2);
        songTimes.add(song3);
        songTimes.add(song4);
        songTimes.add(song5);
        songTimes.add(song6);
        songTimes.add(song7);
        songTimes.add(song8);
        songTimes.add(song9);

        tutorial1 = (LinearLayout) findViewById(R.id.tutorial1);
        tutorial2 = (LinearLayout) findViewById(R.id.tutorial2);
        tutorial3 = (LinearLayout) findViewById(R.id.tutorial3);
        tutorial4 = (LinearLayout) findViewById(R.id.tutorial4);
        tutorial5 = (LinearLayout) findViewById(R.id.tutorial5);
        tutorial6 = (LinearLayout) findViewById(R.id.tutorial6);
        tutorial1Arrow = (ImageView) findViewById(R.id.tutorial1Arrow);
        tutorial3Arrow = (ImageView) findViewById(R.id.tutorial3Arrow);
        tutorial4Arrow = (ImageView) findViewById(R.id.tutorial4Arrow);
        tutorial5Arrow = (ImageView) findViewById(R.id.tutorial5Arrow);

        leftView = (RelativeLayout) findViewById(R.id.leftView);

        Intent intent = getIntent();
        packageNumber = intent.getIntExtra("packageNumber", 0);
        packageName = intent.getStringExtra("packageName");
        neededScore = intent.getIntExtra("scoreTobeat",0);
        scoreToBeat.setText(String.valueOf(neededScore));
        barTitle.setText(intent.getStringExtra("name"));
        cost = intent.getIntExtra("cost", 0);
        levelNumber.setText(String.valueOf(intent.getIntExtra("selectedLevel", 0)));
        level = intent.getIntExtra("selectedLevel", 0);
        levelNumber.setText(String.valueOf(level));

        wrongSelected = (ImageView)findViewById(R.id.border);

        fillSongThumbs();

        // Mediaplayer
        mp = new MediaPlayer();
        songManager = new SongsManager();

        // Listeners
        mp.setOnCompletionListener(this); // Important

        // Getting all songs list
        songsList = songManager.getPlayList();
        songsList = shufleArray(songsList);
        if(songsList.size()<1){

            dialog = new AlertViewPink(this, "Something went wrong","Prooblems on the server");
            dialog.show();
            mp.pause();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mp.start();
                }
            });
        }


        firstRun = getSharedPreferences("iis.production.musingo.main", MODE_PRIVATE);
        firstBonus = getSharedPreferences("iis.production.musingo.main", MODE_PRIVATE);
        if(firstRun.getBoolean("firstRun", true)){
            tutorial1.setVisibility(View.VISIBLE);

        }else{
            // By default play first song
            playSong(0);
        }

    }

    public void goBackButton(View view){
        MusingoApp.soundButton();
        finish();
    }

    public void toTokenShop(View view){
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, TokenShopActivity.class);
        startActivity(intent);
    }

    /**
     * Function to play a song
     * @param songIndex - index of song
     * */
    public void  playSong(int songIndex){
        maximumTime = 10;
        Log.v("Musingo", "song now --  "+ songsList.get(songIndex).get("songPath"));
        currentSong = songIndex;
        setSeekBaronPosition(songIndex);
        // Play song
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).get("songPath"));
            mp.prepare();
            mp.start();
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showPowerups();
    }
    /**
     *  Move seek bar on position
     * */
    public void setSeekBaronPosition(int index){

    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying time completed playing
            int currentPosition = Integer.parseInt(Utility.milliSecondsToTimer(currentDuration));
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (5 * currentPosition * scale + 0.5f);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
            if(!freeze)
                params.width = pixels;
            bar.setLayoutParams(params);
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
            previousSongTime = currentPosition;
            if(currentPosition == 2 && currentSong != 0)
                Utility.setBackgroundBySDK(wrongSelected,null);
            if(currentPosition == maximumTime)
                playNext();

        }
    };

    private Runnable mBonusTask = new Runnable() {
        public void run() {
            if(waitingForResultActivity){
                toResultsList();
            }
            bonusOpen = false;
            hideBonus();

        }
    };

    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.removeCallbacks(mBonusTask);
        mp.release();
        finish();
    }

    public void playNext(){
        checkForBonuses();
        findViewById(R.id.pauseIndicator).setVisibility(View.GONE);
        for(RelativeLayout hint : hintSelected){
            Utility.setBackgroundBySDK(hint.findViewById(R.id.borderRight),null);
        }
        hintSelected = new ArrayList<RelativeLayout>();

        RelativeLayout view = songTimes.get(currentSong);
        if(!skipBorder){
            if(previousSongTime == 10 || !userRight ){
                view.setBackgroundResource(R.drawable.round_song_wrong);
                saveSongResult("-");
                TextViewPacifico text = (TextViewPacifico)view.findViewById(R.id.title);
                text.setText("-");
                MusingoApp.soundWrong();
            }else if(previousSongTime < 10 && userRight){
                view.setBackgroundResource(R.drawable.round_song_right);
                TextViewPacifico text = (TextViewPacifico)view.findViewById(R.id.title);
                if(freeze){
                    previousSongTime = freezeTime;
                    freeze = false;
                    findViewById(R.id.pauseIndicator).setVisibility(View.GONE);
                }
                saveSongResult(String.valueOf(previousSongTime));
                text.setText(String.valueOf(previousSongTime) + "s");
            }
        }
        skipBorder = false;
        if(currentSong != 8 && !replay){
            final float scale = getResources().getDisplayMetrics().density;
            int left = Math.round(offset * ((float)currentSong+1)) + currentSong;
            int pixels = (int) (left * scale + 0.5f);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) seekBar.getLayoutParams();
            params.setMargins(pixels, 0, 0, 0);
            seekBar.setLayoutParams(params);

            pixels= (int) (30 * scale) + pixels;
            LinearLayout.LayoutParams tutorialParams = (LinearLayout.LayoutParams) tutorial1Arrow.getLayoutParams();
            tutorialParams.setMargins(pixels, 0, 0, 0);
            tutorial1Arrow.setLayoutParams(tutorialParams);
            playSong(currentSong + 1);

        }else if(skip && currentSong == 8){
            final float scale = getResources().getDisplayMetrics().density;
            int left = Math.round(offset * ((float)currentSong+1)) + currentSong;
            int pixels = (int) (left * scale + 0.5f);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) seekBar.getLayoutParams();
            params.setMargins(pixels, 0, 0, 0);
            seekBar.setLayoutParams(params);
            playSong(skippedSong);
            skip = false;
            currentSong = 8;
        }else if(replay){
            final float scale = getResources().getDisplayMetrics().density;

            int left = Math.round(offset * ((float)currentSong+1)) + currentSong;
            int pixels = (int) (left * scale + 0.5f);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) seekBar.getLayoutParams();
            params.setMargins(pixels, 0, 0, 0);
            seekBar.setLayoutParams(params);
            playSong(currentSong);
            replay = false;
        }else{
            mHandler.removeCallbacks(mUpdateTimeTask);
            mp.stop();
            if(tutorialOpen || bonusOpen){
                waitingForResultActivity = true;
            }
            else{
                toResultsList();
            }
        }
        freeze = false;
        findViewById(R.id.pauseIndicator).setVisibility(View.GONE);
    }

    public void fillSongThumbs(){
        songs = LevelSelectionActivity.gameSongs;

        for (int i = 0; i<songs.size(); i++){
            Song song = songs.get(i);
            RelativeLayout view = (RelativeLayout)songThumbs.get(i);
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            TextViewArchitects textView = (TextViewArchitects)view.findViewById(R.id.title);
            view.setTag(song.getId());
            allIndexes.put(song.getId(),i);
            final RoundedCornersDrawable drawable = new RoundedCornersDrawable(getResources(), song.getImage());
            imageView.setImageDrawable(drawable);
            textView.setText(song.getSongName());


        }
    }

    public void tryToGuess(View view){
        if(maxTryes != 9){
            maxTryes += 1;
            for(RelativeLayout hint : hintSelected){
                Utility.setBackgroundBySDK(hint,null);
            }
            hintSelected = new ArrayList<RelativeLayout>();
            if(currentSong < 9){
                String id = songsList.get(currentSong).get("songTitle");

                if(currentSong != 0 && !userRight){
                    Utility.setBackgroundBySDK(wrongSelected,null);
                }
                ImageView image = (ImageView)view.findViewById(R.id.image);
                int width = image.getMeasuredWidth();
                if (view.getTag().toString().equals(id)){
                    MusingoApp.soundCorrect();
                    ImageView borderRight = (ImageView)view.findViewById(R.id.borderRight);
                    borderRight.setLayoutParams(new RelativeLayout.LayoutParams(width + 4, width + 4));
                    borderRight.setBackgroundResource(R.drawable.round_song_big_right);
                    userRight = true;
                    score += cost;
                    yourScore.setText(String.valueOf(score));
                    correctSongs++;
                    int index = allIndexes.get(view.getTag().toString());
                    correctIndexes.add(index);
                }else{
                    Log.v("Musingo", "Width "+width);
                    MusingoApp.soundWrong();
                    ImageView border = (ImageView) view.findViewById(R.id.border);
                    border.setLayoutParams(new RelativeLayout.LayoutParams(width + 4, width + 4));
                    border.setBackgroundResource(R.drawable.round_song_big_wrong);
                    wrongSelected = border;
                    userRight = false;
                }
                playNext();
            }
        }
    }

    public void saveSongResult(String result){
        String id = songsList.get(currentSong).get("songTitle");
        for(int i=0; i < songs.size(); i++){
            Song song = songs.get(i);
            if(id.equals(song.getId())){
                song.setTime(result);
                songsWithTime.add(song);
                Log.v("Musingo", "song " + id);
                for(int y=0; y<availableForHint.size(); y++){
                    Log.v("Musingo", "AAAAAAAAA "+availableForHint.get(y).getId());
                    if(availableForHint.get(y).getId().equals(id) && !result.equals("-")){
                        availableForHint.remove(y);
                        break;
                    }
                }
            }
        }
    }

    public void toResultsList(){
        mHandler.removeCallbacks(mBonusTask);
        AlertViewPink view = new AlertViewPink(this, "Horray!", "you earned \n" + yourScore.getText());
        Log.v("Musingo", "Level toResult : " + level);
        if((score - neededScore) > 0){
            scoreBeaten = true;
        }
        if(correctSongs == 9){
            allSongsCorrect = true;
        }
        Intent intent = new Intent();
        intent.setClass(this, ResultsActivity.class);
        intent.putExtra("currentScore", String.valueOf(score));
        intent.putExtra("levelName", barTitle.getText());
        intent.putExtra("levelNumber", level);
        intent.putExtra("packageNumber", packageNumber);
        intent.putExtra("packageName", packageName);
        intent.putExtra("boostStar", powerUpUsed);
        intent.putExtra("beatStar", scoreBeaten);
        intent.putExtra("completeStar", allSongsCorrect);

        startActivity(intent);
    }

    public void mixStartingPlaylist(String level, String title){
        JSONObject props = new JSONObject();
        try {
            props.put("Level", "Level #"+level);
            props.put("Title", title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MusingoApp app = (MusingoApp)getApplication();
        app.getMixpanelobj().track("Starting a playlist", props);
    }

    public void mixCompletionPlaylist(boolean win, String title, boolean powerUpUsed, boolean tokenUsed, String level){
        JSONObject props = new JSONObject();
        try {
            props.put("Win or lose", win);
            props.put("Title", title);
            if(powerUpUsed){
                props.put("Power Ups", "Power up used");
            }
            if(tokenUsed){
                props.put("Token used", "Token used");
            }
            props.put("Level", "Level #"+level);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MusingoApp app = (MusingoApp)getApplication();
        app.getMixpanelobj().track("Completing a playlist", props);
    }

    public ArrayList<HashMap<String, String>> shufleArray(ArrayList<HashMap<String, String>> array){
        Log.v("Musingo", "before" + array.get(0) + "size -"+array.size());
        ArrayList<HashMap<String, String>> newArray = new ArrayList<HashMap<String, String>>();
        Random rnd = new Random();
        HashMap<Integer, Integer> indexes = new HashMap<Integer, Integer>();
        while(newArray.size() != 9){
            int index = rnd.nextInt(9);
            if(indexes.containsKey(index)){

            }else{
                Log.v("Musingo", "rand "+index);
                HashMap<String, String> a = array.get(index);
                newArray.add(a);
                indexes.put(index, index);
            }

        }
        Log.v("Musingo", "after" + newArray.get(0) + "size -"+newArray.size());
        return newArray;
    }
// ----- Powerups
    public void showPowerups(){
        ImageView image;
        PlaySongsTable table = new PlaySongsTable(this);
        int levelsPlayed = table.getPlayedLevelsByPackage(packageName);
        boolean needToShow = false;

        String title = "";
        String body = "";

        if(levelsPlayed == 1){
            image = (ImageView)hintHint.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_hint_vis);
            image.setTag("vis");
            image = (ImageView)hintHint.findViewById(R.id.lock);
            image.setVisibility(View.GONE);
            needToShow = true;
            title = getString(R.string.hint_powerup_title);
            body = getString(R.string.hint_powerup_body);
        }

        if(levelsPlayed == 2){
            image = (ImageView)hintSkip.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_skip_vis);
            image.setTag("vis");
            image = (ImageView)hintSkip.findViewById(R.id.lock);
            image.setVisibility(View.GONE);
            needToShow = true;
            title = getString(R.string.skip_powerup_title);
            body = getString(R.string.skip_powerup_body);
        }

        if(levelsPlayed == 3){
            image = (ImageView)hintReplay.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_replay_vis);
            image.setTag("vis");
            image = (ImageView)hintReplay.findViewById(R.id.lock);
            image.setVisibility(View.GONE);
            needToShow = true;
            title = getString(R.string.replay_powerup_title);
            body = getString(R.string.replay_powerup_body);
        }

        if(levelsPlayed == 5){
            image = (ImageView)hintFreeze.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_freeze_vis);
            image.setTag("vis");
            image = (ImageView)hintFreeze.findViewById(R.id.lock);
            image.setVisibility(View.GONE);
            needToShow = true;
            title = getString(R.string.replay_powerup_title);
            body = getString(R.string.replay_powerup_body);

        }

        if(levelsPlayed == 7){
            image = (ImageView)hintLonger.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_longer_vis);
            image.setTag("vis");
            image = (ImageView)hintLonger.findViewById(R.id.lock);
            image.setVisibility(View.GONE);
            needToShow = true;
            title = getString(R.string.longer_clip_powerup_title);
            body = getString(R.string.longer_clip_powerup_body);
        }

        if(levelsPlayed == 8){
            image = (ImageView)hintNextList.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_nextlist_vis);
            image.setTag("vis");
            image = (ImageView)hintNextList.findViewById(R.id.lock);
            image.setVisibility(View.GONE);
            needToShow = true;
            title = getString(R.string.next_playlist_powerup_title);
            body = getString(R.string.next_playlist_powerup_body);
        }

        if(needToShow && !mSettings.getBoolean(title, false)){
            dialog = new AlertViewOrange("Hint Powerup" , title, body, this);
            dialog.show();
            mSettings.edit().putBoolean(title,true).commit();
            mp.pause();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mp.start();
                }
            });
        }

    }

    public void powerUpFB(View view){
        String id = songsList.get(currentSong).get("songTitle");
        String uuid = "";
        String image = null;

        for (int i=0; i < songs.size(); i++){
            if (id.equals(songs.get(i).getId())){
                uuid = songs.get(i).getUuid();
                image = songs.get(i).getImageUrl();
            }
        }
        Bundle params = new Bundle();
        params.putString("name", "Help!");
        params.putString("caption", "Who can name this song from the playlist '" + packageName + "'");
        params.putString("description", "Open this link to hear the tune.");
        params.putString("link", getString(R.string.fb_hint) + uuid);
        params.putString("picture", image);

        mp.pause();
        FacebookManager.PostFb(MainGameActivity.this, params, new Facebook.DialogListener() {
            @Override
            public void onComplete(Bundle values) {
                mp.start();
            }

            @Override
            public void onFacebookError(FacebookError e) {

            }

            @Override
            public void onError(DialogError e) {

            }

            @Override
            public void onCancel() {
                mp.start();
            }
        });
    }

    public void powerUpHint(View view){
        int cost = Integer.parseInt(getString(R.string.cost_hint));

        if(view.getTag().toString().equals("vis")){
            MusingoApp.soundButton();
            if(availableForHint.size() > 4 && powerupPurcase(cost)){
                powerUpUsed = true;
                ImageView image = (ImageView)hintHint.findViewById(R.id.hint);
                image.setImageResource(R.drawable.hint_hint_inv);
                view.setTag("inv");
                hintAction();
            }
        }
    }

    public void powerUpSkip(View view){
        int cost = Integer.parseInt(getString(R.string.cost_skip));


        if(view.getTag().toString().equals("vis")){
            MusingoApp.soundButton();
            powerUpUsed = true;
            ImageView image = (ImageView)hintSkip.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_skip_inv);
            view.setTag("inv");
            skipAction();
        }

    }

    public void powerUpReplay(View view){
        int cost = Integer.parseInt(getString(R.string.cost_replay));


        if(view.getTag().toString().equals("vis")){
            MusingoApp.soundButton();
            powerUpUsed = true;
            ImageView image = (ImageView)hintReplay.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_replay_inv);
            view.setTag("inv");
            replayAction();

        }
    }

    public void powerUpFreeze(View view){
        int cost = Integer.parseInt(getString(R.string.cost_freeze));

        if(view.getTag().toString().equals("vis")){
            MusingoApp.soundButton();
            powerUpUsed = true;
            ImageView image = (ImageView)hintFreeze.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_freeze_inv);
            view.setTag("inv");
            freezeAction();
        }
    }

    public void powerUpLonger(View view){
        int cost = Integer.parseInt(getString(R.string.cost_longer));

        if(view.getTag().toString().equals("vis")){
            MusingoApp.soundButton();
            powerUpUsed = true;
            ImageView image = (ImageView)hintLonger.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_longer_inv);
            view.setTag("inv");
            longerAction();
        }

    }
    public void powerUpNextList(View view){
        int cost = Integer.parseInt(getString(R.string.cost_next));

        if(view.getTag().toString().equals("vis")){
            MusingoApp.soundButton();
            powerUpUsed = true;
            ImageView image = (ImageView)hintNextList.findViewById(R.id.hint);
            image.setImageResource(R.drawable.hint_nextlist_inv);
            view.setTag("inv");
            nextAction();
        }

    }

    public boolean powerupPurcase(int cost){
        int currentTokens = mSettings.getInt("tokens",0);
        Log.v("Musingo", "current "+ currentTokens + "cost " + cost);
        if((currentTokens - cost) < 0){
            return false;
        }else{
            tokens = (TextView)findViewById(R.id.coins);
            mSettings.edit().putInt("tokens",currentTokens - cost).commit();
            tokens.setText(String.valueOf(mSettings.getInt("tokens",0)));
            return true;
        }
    }

    public void hintAction(){
        for(RelativeLayout hint : hintSelected){
            Utility.setBackgroundBySDK(hint,null);
        }
        hintSelected = new ArrayList<RelativeLayout>();

        int random1 = 0;
        String id = songsList.get(currentSong).get("songTitle");
        for(int i=0; i<songThumbs.size(); i++){
            if(songThumbs.get(i).getTag().toString().equals(id)){
                random1 = i;
            }
        }
        int random2 = 0;
        int random3 = 0;
        while(random2 == random1){
            random2 = getRandomSong();
        }
        while(random3 == random1 || random3 == random2){
            random3 = getRandomSong();
        }
        Log.v("Musingo", "random1 "+random1);
        Log.v("Musingo", "random2 "+random2);
        Log.v("Musingo", "random3 "+random3);
        Log.v("Musingo", "ava "+availableForHint.size());

        ImageView image = (ImageView)songThumbs.get(random1).findViewById(R.id.image);
        int width = image.getMeasuredWidth();
        ImageView borderRight = (ImageView)songThumbs.get(random1).findViewById(R.id.borderRight);
        borderRight.setLayoutParams(new RelativeLayout.LayoutParams(width + 4, width + 4));
        borderRight.setBackgroundResource(R.drawable.round_song_hint);
        borderRight = (ImageView)songThumbs.get(random2).findViewById(R.id.borderRight);
        borderRight.setLayoutParams(new RelativeLayout.LayoutParams(width + 4, width + 4));
        borderRight.setBackgroundResource(R.drawable.round_song_hint);
        borderRight = (ImageView)songThumbs.get(random3).findViewById(R.id.borderRight);
        borderRight.setLayoutParams(new RelativeLayout.LayoutParams(width + 4, width + 4));
        borderRight.setBackgroundResource(R.drawable.round_song_hint);

        hintSelected.add(songThumbs.get(random1));
        hintSelected.add(songThumbs.get(random2));
        hintSelected.add(songThumbs.get(random3));
    }

    public int getRandomSong(){
        Random r = new Random();
        int random = r.nextInt(availableForHint.size());
        for(int i=0; i<availableForHint.size(); i++){
            Log.v("Musingo", "idddddd "+availableForHint.get(random).getId());
            String rand1id = songThumbs.get(i).getTag().toString();
            String thatId = availableForHint.get(random).getId();
            if(rand1id.equals(thatId)){
                return i;
            }
        }
        return 0;
    }
    public void skipAction(){
        skippedSong = currentSong;
        skip = true;
        skipBorder = true;
        hintTextImage.setImageResource(R.drawable.skip_text_image);
        hintTextImage.setVisibility(View.VISIBLE);
        hintTextImage.setAnimation(inFromRightAnimation());
        hintTextImage.startAnimation(inFromRightAnimation());
        playNext();

    }

    public void replayAction(){

        replay = true;
        hintTextImage.setImageResource(R.drawable.replay_text_image);
        hintTextImage.setVisibility(View.VISIBLE);
        hintTextImage.setAnimation(inFromRightAnimation());
        hintTextImage.startAnimation(inFromRightAnimation());
        playNext();
    }

    public void longerAction(){
        maximumTime +=10;
        hintTextImage.setImageResource(R.drawable.longer_text_image);
        hintTextImage.setVisibility(View.VISIBLE);
        hintTextImage.setAnimation(inFromRightAnimation());
        hintTextImage.startAnimation(inFromRightAnimation());
    }

    public void freezeAction(){
        freeze = true;
        freezeTime = Integer.parseInt(Utility.milliSecondsToTimer(mp.getCurrentPosition()));
        findViewById(R.id.pauseIndicator).setVisibility(View.VISIBLE);

    }

    public void nextAction(){
        Intent intent = new Intent();
        setResult(LevelSelectionActivity.NEXT_LEVEL, intent);
        finish();
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public void hideHint(View view){
        hintTextImage.setVisibility(View.GONE);
    }
// - Tutorials
    public void nextTutorial(View v){
        LinearLayout.LayoutParams params;
        final float scale = getResources().getDisplayMetrics().density;
        int marginLeft;
        int marginRight;
        int marginBottom;

        switch (v.getId()){
            case R.id.tutorial1 :
                tutorial1.setVisibility(View.GONE);
                tutorial2.setVisibility(View.VISIBLE);
                break;
            case R.id.tutorial2 :
                tutorial2.setVisibility(View.GONE);
                tutorial3.setVisibility(View.VISIBLE);

                params = (LinearLayout.LayoutParams) tutorial3Arrow.getLayoutParams();
                marginLeft = leftView.getLeft() + leftView.getWidth()/2;
                params.setMargins(marginLeft, 0, 0, 0);
                tutorial3Arrow.setLayoutParams(params);

                break;
            case R.id.tutorial3 :
                tutorial3.setVisibility(View.GONE);
                tutorial3.setVisibility(View.GONE);
                tutorial4.setVisibility(View.VISIBLE);

                marginRight = leftView.getLeft() + leftView.getWidth()/2;
                params = (LinearLayout.LayoutParams) tutorial4Arrow.getLayoutParams();
                params.setMargins(0, 0, marginRight, 0);
                tutorial4Arrow.setLayoutParams(params);
                break;
            case R.id.tutorial4 :
                tutorial4.setVisibility(View.GONE);
                tutorial5.setVisibility(View.VISIBLE);

                marginRight = (int) (17 * scale);
                marginBottom = (int) (53 * scale);
                Log.v("Musingo", "scale: " + scale);
                params = (LinearLayout.LayoutParams) tutorial5Arrow.getLayoutParams();
                params.setMargins(marginRight, 0, 0, marginBottom);
                tutorial5Arrow.setLayoutParams(params);
                break;
            case R.id.tutorial5 :
                playSong(0);
                tutorial5.setVisibility(View.GONE);
                SharedPreferences.Editor editor = firstRun.edit();
                editor.putBoolean("firstRun", false);
                editor.commit();
                break;
            case R.id.tutorial6 :
                mp.start();
                tutorial6.setVisibility(View.GONE);
                ImageView imageView = (ImageView)findViewById(R.id.bonusText);
                imageView.setVisibility(View.GONE);
                if(waitingForResultActivity){
                    toResultsList();
                }
                break;
        }
    }

// -- Bonuses
    public void checkForBonuses(){
        for(Integer i : correctIndexes){
            Log.v("Musingo", "index "+i);
        }
        if(correctSongs == 9){
            setBonusImage(R.drawable.bonus_leave_no);
            score = score * 4;
            firstBonusTutorial(R.drawable.bonus_leave_no);
            bonusOpen = true;
            mHandler.postDelayed(mBonusTask, 2000);
        }else if(correctIndexes.contains(0) && correctIndexes.contains(4) && correctIndexes.contains(8)){
            if(crissCrossBonus){
                setBonusImage(R.drawable.bonus_criss_cross);
                crissCrossBonus = false;
                score = score * 2;
                firstBonusTutorial(R.drawable.bonus_criss_cross);
                bonusOpen = true;
                mHandler.postDelayed(mBonusTask, 2000);
            }
        }else if(correctIndexes.contains(0) && correctIndexes.contains(2) && correctIndexes.contains(4) && correctIndexes.contains(7) && correctIndexes.contains(8)){
            if(diagonalsBonus){
                setBonusImage(R.drawable.bonus_new_diagonal);
                diagonalsBonus = false;
                score = score + 100;
                firstBonusTutorial(R.drawable.bonus_new_diagonal);
                bonusOpen = true;
                mHandler.postDelayed(mBonusTask, 2000);
            }
        }else if(correctIndexes.contains(0) && correctIndexes.contains(1) && correctIndexes.contains(2) ||
                correctIndexes.contains(3) && correctIndexes.contains(4) && correctIndexes.contains(5) ||
                correctIndexes.contains(6) && correctIndexes.contains(7) && correctIndexes.contains(8)){
            if(squaresRowBonus){
                setBonusImage(R.drawable.bonus_three_squares_inrow);
                squaresRowBonus = false;
                score = score + 50;
                firstBonusTutorial(R.drawable.bonus_three_squares_inrow);
                bonusOpen = true;
                mHandler.postDelayed(mBonusTask, 2000);
            }
        }else if(correctIndexes.contains(0) && correctIndexes.contains(3) && correctIndexes.contains(6) ||
                correctIndexes.contains(1) && correctIndexes.contains(4) && correctIndexes.contains(7) ||
                correctIndexes.contains(2) && correctIndexes.contains(5) && correctIndexes.contains(8)){
            if(squaresBonus){
                setBonusImage(R.drawable.bonus_three_squares);
                squaresBonus = false;
                score = score + 50;
                firstBonusTutorial(R.drawable.bonus_three_squares);
                bonusOpen = true;
                mHandler.postDelayed(mBonusTask, 2000);
            }
        }else if(correctIndexes.contains(0) && correctIndexes.contains(2) && correctIndexes.contains(6) && correctIndexes.contains(8)){
            if(cornersBonus){
                setBonusImage(R.drawable.bonus_4_corners);
                cornersBonus = false;
                score = score + 150;
                firstBonusTutorial(R.drawable.bonus_4_corners);
                bonusOpen = true;
                mHandler.postDelayed(mBonusTask, 2000);
            }
        }

        yourScore.setText(String.valueOf(score));

    }

    public void setBonusImage(int res){
        ImageView imageView = (ImageView)findViewById(R.id.bonusText);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(res);
    }

    public void hideBonus(){
        ImageView imageView = (ImageView)findViewById(R.id.bonusText);
        imageView.setVisibility(View.GONE);
    }

    public void firstBonusTutorial(int bonusMessage){
        if(firstBonus.getBoolean("firstBonus", true)){
            mp.pause();
//            ImageView imageView = (ImageView) findViewById(R.id.bonusTutorial);
            tutorial6.setVisibility(View.VISIBLE);
//            imageView.setImageResource(bonusMessage);
            SharedPreferences.Editor editor = firstBonus.edit();
            editor.putBoolean("firstBonus", false);
            editor.commit();

            tutorialOpen = true;
        }
    }
}
