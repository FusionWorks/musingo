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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    int currentSong;
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

    ImageView hintfb;
    ImageView hintHint;
    ImageView hintSkip;
    ImageView hintFreeze;
    ImageView hintReplay;
    ImageView hintLonger;
    ImageView hintNextList;

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

    int packageNumber;
    String packageName;
    int neededScore;
    boolean powerUpUsed;
    boolean scoreBeaten;
    boolean allSongsCorrect;

    ArrayList<ImageView> hintSelected;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        seekBar = (RelativeLayout)findViewById(R.id.seekBar);
        bar = (ImageView)seekBar.findViewById(R.id.bar);
        barTitle = (TextViewPacifico)findViewById(R.id.barTitle);
        scoreToBeat = (TextViewArchitects)findViewById(R.id.scoreToBeat);
        yourScore = (TextViewArchitects)findViewById(R.id.yourScore);
        levelNumber = (TextViewPacifico)findViewById(R.id.levelNumber);
        songs = new ArrayList<Song>();
        songsWithTime = new ArrayList<Song>();
        tokens = (TextView)findViewById(R.id.coins);
        tokens.setText(String.valueOf(mSettings.getInt("tokens",0)));
        powerUpUsed = false;
        scoreBeaten = false;
        allSongsCorrect = false;
        correctSongs = 0;
        hintSelected = new ArrayList<ImageView>();
        //Powerups icons
        hintfb = (ImageView)findViewById(R.id.hintfb);
        hintHint = (ImageView)findViewById(R.id.hintHint);
        hintSkip = (ImageView)findViewById(R.id.hintSkip);
        hintFreeze = (ImageView)findViewById(R.id.hintFreeze);
        hintReplay = (ImageView)findViewById(R.id.hintReplay);
        hintLonger = (ImageView)findViewById(R.id.hintLonger);
        hintNextList = (ImageView)findViewById(R.id.hintNextList);

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
        showPowerups();

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
        // By default play first song
        playSong(0);

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
            params.width = pixels;
            bar.setLayoutParams(params);
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
            previousSongTime = currentPosition;
            if(currentPosition == 2 && currentSong != 0)
                Utility.setBackgroundBySDK(wrongSelected,null);
            if(currentPosition == 10){
                playNext();
            }
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
        mp.release();
        finish();
    }

    public void playNext(){
        RelativeLayout view = songTimes.get(currentSong);
        if(previousSongTime == 10 || !userRight){
            view.setBackgroundResource(R.drawable.round_song_wrong);
            saveSongResult("-");
            TextViewPacifico text = (TextViewPacifico)view.findViewById(R.id.title);
            text.setText("-");
        }else if(previousSongTime < 10 && userRight){
            view.setBackgroundResource(R.drawable.round_song_right);
            saveSongResult(String.valueOf(previousSongTime));
            TextViewPacifico text = (TextViewPacifico)view.findViewById(R.id.title);
            text.setText(String.valueOf(previousSongTime) + "s");
        }
        if(currentSong != 8){
            final float scale = getResources().getDisplayMetrics().density;
            int left = 35 * (currentSong + 1);
            int pixels = (int) (left * scale + 0.5f);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) seekBar.getLayoutParams();
            params.setMargins(pixels, 0, 0, 0);
            seekBar.setLayoutParams(params);
            playSong(currentSong + 1);

        }else{
            mHandler.removeCallbacks(mUpdateTimeTask);
            mp.stop();
            toResultsList();
        }
    }

    public void fillSongThumbs(){
        songs = LevelSelectionActivity.gameSongs;

        for (int i = 0; i<songs.size(); i++){
            Song song = songs.get(i);
            RelativeLayout view = (RelativeLayout)songThumbs.get(i);
            ImageView imageView = (ImageView)view.findViewById(R.id.image);
            TextViewArchitects textView = (TextViewArchitects)view.findViewById(R.id.title);
            view.setTag(song.getId());
            final RoundedCornersDrawable drawable = new RoundedCornersDrawable(getResources(), song.getImage());
            imageView.setImageDrawable(drawable);

            textView.setText(song.getSongName());
        }
    }

    public void tryToGuess(View view){
        if(currentSong < 9){
            String id = songsList.get(currentSong).get("songTitle");

            for(ImageView hint : hintSelected){
                Utility.setBackgroundBySDK(hint,null);
            }
            hintSelected = new ArrayList<ImageView>();

            if(currentSong != 0 && !userRight){
                Utility.setBackgroundBySDK(wrongSelected,null);
            }
            if (view.getTag().toString().equals(id)){
                MusingoApp.soundCorrect();
                view.setBackgroundResource(R.drawable.round_song_big_right);
                userRight = true;
                score += cost;
                yourScore.setText(String.valueOf(score));
                correctSongs++;
            }else{
                MusingoApp.soundWrong();
                view.findViewById(R.id.border).setBackgroundResource(R.drawable.round_song_big_wrong);
                wrongSelected = (ImageView)view.findViewById(R.id.border);
                userRight = false;
            }
            playNext();
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
                break;
            }
        }
    }

    public void toResultsList(){
//        AlertViewPink view = new AlertViewPink(this, "Horray!", "you earned \n" + yourScore.getText());
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

    public void showPowerups(){
        if(mSettings.getBoolean("hint",false)){
            hintHint.setImageResource(R.drawable.hint_hint_vis);
            hintHint.setTag("vis");
        }
        if(mSettings.getBoolean("skip",false)){
            hintSkip.setImageResource(R.drawable.hint_skip_vis);
            hintSkip.setTag("vis");
        }
        if(mSettings.getBoolean("replay",false)){
            hintReplay.setImageResource(R.drawable.hint_replay_vis);
            hintReplay.setTag("vis");
        }
        if(mSettings.getBoolean("freeze",false)){
            hintFreeze.setImageResource(R.drawable.hint_freeze_vis);
            hintFreeze.setTag("vis");
        }
        if(mSettings.getBoolean("longer",false)){
            hintLonger.setImageResource(R.drawable.hint_longer_vis);
            hintLonger.setTag("vis");
        }
        if(mSettings.getBoolean("next", false)){
            hintNextList.setImageResource(R.drawable.hint_nextlist_vis);
            hintNextList.setTag("vis");
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
            if(view.getTag().toString().equals("inv")){
                if(powerupPurcase(cost)){
                    mSettings.edit().putBoolean("hint",true).commit();
                    hintHint.setImageResource(R.drawable.hint_hint_vis);
                    view.setTag("vis");
                    String title = getString(R.string.hint_powerup_title);
                    String body = getString(R.string.hint_powerup_body);
                    dialog = new AlertViewOrange("Hint Powerup" , title, body, this);
                    dialog.show();
                    mp.pause();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mp.start();
                        }
                    });
                }else{
                    dialog = new AlertViewOrange("Next Playlist Powerup" , getString(R.string.alert_title), getString(R.string.alert_not_enough_money), this);
                }
            }else if(view.getTag().toString().equals("vis")){
                powerUpUsed = true;
                mSettings.edit().putBoolean("hint",false).commit();
                hintHint.setImageResource(R.drawable.hint_hint_inv);
                view.setTag("inv");
                int random1 = 0;
                String id = songsList.get(currentSong).get("songTitle");
                for(int i=0; i<songThumbs.size(); i++){
                    if(songThumbs.get(i).getTag().toString().equals(id)){
                        ImageView selected = (ImageView)findViewById(R.id.border);
                        selected.setBackgroundResource(R.drawable.round_song_hint);
                        random1 = i;
                    }
                }
                Random r = new Random();
                int random2 = 0;
                int random3 = 0;
                while(random1 == random2 || random1 == random3){
                    random1 = r.nextInt(9);
                }
                while(random2 == random1 || random2 == random3){
                    random2 = r.nextInt(9);
                }
                songThumbs.get(random2).findViewById(R.id.border).setBackgroundResource(R.drawable.round_song_hint);
                songThumbs.get(random3).findViewById(R.id.border).setBackgroundResource(R.drawable.round_song_hint);
                hintSelected.add((ImageView)songThumbs.get(random1).findViewById(R.id.border));
                hintSelected.add((ImageView)songThumbs.get(random2).findViewById(R.id.border));
                hintSelected.add((ImageView)songThumbs.get(random3).findViewById(R.id.border));
            }
    }

    public void powerUpSkip(View view){
        int cost = Integer.parseInt(getString(R.string.cost_skip));
        if(view.getTag().toString().equals("inv")){
            if(powerupPurcase(cost)){
                mSettings.edit().putBoolean("skip",true).commit();
                hintSkip.setImageResource(R.drawable.hint_skip_vis);
                view.setTag("vis");
                String title = getString(R.string.skip_powerup_title);
                String body = getString(R.string.skip_powerup_body);
                dialog = new AlertViewOrange("Skip Playlist Powerup" , title, body, this);
                dialog.show();
                mp.pause();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mp.start();
                    }
                });
            }else{
                dialog = new AlertViewOrange("Next Playlist Powerup" , getString(R.string.alert_title), getString(R.string.alert_not_enough_money), this);
            }
        }else if(view.getTag().toString().equals("vis")){
            powerUpUsed = true;
            mSettings.edit().putBoolean("skip",false).commit();
            hintSkip.setImageResource(R.drawable.hint_skip_inv);
            view.setTag("inv");
        }

    }

    public void powerUpReplay(View view){
        int cost = Integer.parseInt(getString(R.string.cost_replay));
        if(view.getTag().toString().equals("inv")){
            if(powerupPurcase(cost)){
                mSettings.edit().putBoolean("replay",true).commit();
                hintReplay.setImageResource(R.drawable.hint_replay_vis);
                view.setTag("vis");
                String title = getString(R.string.replay_powerup_title);
                String body = getString(R.string.replay_powerup_body);
                dialog = new AlertViewOrange("Replay Powerup" , title, body, this);
                dialog.show();
                mp.pause();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mp.start();
                    }
                });
            }else{
                dialog = new AlertViewOrange("Next Playlist Powerup" , getString(R.string.alert_title), getString(R.string.alert_not_enough_money), this);
            }
        }else if(view.getTag().toString().equals("vis")){
            powerUpUsed = true;
            mSettings.edit().putBoolean("replay",false).commit();
            hintReplay.setImageResource(R.drawable.hint_replay_inv);
            view.setTag("inv");
        }
    }

    public void powerUpFreeze(View view){
        int cost = Integer.parseInt(getString(R.string.cost_freeze));
        if(view.getTag().toString().equals("inv")){
            if(powerupPurcase(cost)){
                mSettings.edit().putBoolean("freeze",true).commit();
                hintFreeze.setImageResource(R.drawable.hint_freeze_vis);
                view.setTag("vis");
                String title = getString(R.string.freeze_powerup_title);
                String body = getString(R.string.freeze_powerup_body);
                dialog = new AlertViewOrange("Freeze Powerup" , title, body, this);
                dialog.show();
                mp.pause();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mp.start();
                    }
                });
            }else{
                dialog = new AlertViewOrange("Next Playlist Powerup" , getString(R.string.alert_title), getString(R.string.alert_not_enough_money), this);
            }

        }else if(view.getTag().toString().equals("vis")){
            powerUpUsed = true;
            mSettings.edit().putBoolean("freeze",false).commit();
            hintFreeze.setImageResource(R.drawable.hint_freeze_inv);
            view.setTag("inv");
        }
    }

    public void powerUpLonger(View view){
        int cost = Integer.parseInt(getString(R.string.cost_longer));
        if(view.getTag().toString().equals("inv")){
            if(powerupPurcase(cost)){
                mSettings.edit().putBoolean("longer",true).commit();
                hintLonger.setImageResource(R.drawable.hint_longer_vis);
                view.setTag("vis");
                String title = getString(R.string.longer_clip_powerup_title);
                String body = getString(R.string.longer_clip_powerup_body);
                dialog = new AlertViewOrange("Longer Clip Powerup" , title, body, this);
                dialog.show();
                mp.pause();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mp.start();
                    }
                });
            }else{
                dialog = new AlertViewOrange("Next Playlist Powerup" , getString(R.string.alert_title), getString(R.string.alert_not_enough_money), this);
            }
        }else if(view.getTag().toString().equals("vis")){
            powerUpUsed = true;
            mSettings.edit().putBoolean("longer",false).commit();
            hintLonger.setImageResource(R.drawable.hint_longer_inv);
            view.setTag("inv");
        }

    }
    public void powerUpNextList(View view){
        int cost = Integer.parseInt(getString(R.string.cost_next));
        if(view.getTag().toString().equals("inv")){
            if(powerupPurcase(cost)){
                mSettings.edit().putBoolean("next",true).commit();
                hintNextList.setImageResource(R.drawable.hint_nextlist_vis);
                view.setTag("vis");
                String title = getString(R.string.next_playlist_powerup_title);
                String body = getString(R.string.next_playlist_powerup_body);
                dialog = new AlertViewOrange("Next Playlist Powerup" , title, body, this);
                dialog.show();
                mp.pause();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mp.start();
                    }
                });

            }else{
                dialog = new AlertViewOrange("Next Playlist Powerup" , getString(R.string.alert_title), getString(R.string.alert_not_enough_money), this);
            }
        }else if(view.getTag().toString().equals("vis")){
            powerUpUsed = true;
            mSettings.edit().putBoolean("next",false).commit();
            hintNextList.setImageResource(R.drawable.hint_nextlist_inv);
            view.setTag("inv");

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
}
