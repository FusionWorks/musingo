package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.main.more.TokenShopActivity;
import iis.production.musingo.objects.AlertViewPink;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.RoundedCornersDrawable;
import iis.production.musingo.utility.SongsManager;
import iis.production.musingo.utility.Utility;

public class MainGameActivity extends Activity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

    // Views vars
    TextView coins;
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
    Runnable removeViewBorder;

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

    //Media Player vars
    MediaPlayer mp;
    SongsManager songManager;
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        seekBar = (RelativeLayout)findViewById(R.id.seekBar);
        bar = (ImageView)seekBar.findViewById(R.id.bar);
        barTitle = (TextViewPacifico)findViewById(R.id.barTitle);
        scoreToBeat = (TextViewArchitects)findViewById(R.id.scoreToBeat);
        yourScore = (TextViewArchitects)findViewById(R.id.yourScore);
        levelNumber = (TextViewPacifico)findViewById(R.id.levelNumber);
        songs = new ArrayList<Song>();
        songsWithTime = new ArrayList<Song>();

        //Song Thumbs layouts
        RelativeLayout songThumb1 = (RelativeLayout)findViewById(R.id.songThumb1);
        RelativeLayout songThumb2 = (RelativeLayout)findViewById(R.id.songThumb2);
        RelativeLayout songThumb3 = (RelativeLayout)findViewById(R.id.songThumb3);
        RelativeLayout songThumb4 = (RelativeLayout)findViewById(R.id.songThumb4);
        RelativeLayout songThumb5 = (RelativeLayout)findViewById(R.id.songThumb5);
        RelativeLayout songThumb6 = (RelativeLayout)findViewById(R.id.songThumb6);
        RelativeLayout songThumb7 = (RelativeLayout)findViewById(R.id.songThumb7);
        RelativeLayout songThumb8 = (RelativeLayout)findViewById(R.id.songThumb8);
        RelativeLayout songThumb9 = (RelativeLayout)findViewById(R.id.songThumb9);

        //Songs mini timing views
        RelativeLayout song1 = (RelativeLayout)findViewById(R.id.song1);
        RelativeLayout song2 = (RelativeLayout)findViewById(R.id.song2);
        RelativeLayout song3 = (RelativeLayout)findViewById(R.id.song3);
        RelativeLayout song4 = (RelativeLayout)findViewById(R.id.song4);
        RelativeLayout song5 = (RelativeLayout)findViewById(R.id.song5);
        RelativeLayout song6 = (RelativeLayout)findViewById(R.id.song6);
        RelativeLayout song7 = (RelativeLayout)findViewById(R.id.song7);
        RelativeLayout song8 = (RelativeLayout)findViewById(R.id.song8);
        RelativeLayout song9 = (RelativeLayout)findViewById(R.id.song9);

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
        Log.v("Musingo", "score " + intent.getStringExtra("scoreTobeat"));
        scoreToBeat.setText(intent.getStringExtra("scoreTobeat"));
        barTitle.setText(intent.getStringExtra("name"));
        cost = Integer.parseInt(intent.getStringExtra("cost"));
        levelNumber.setText(intent.getStringExtra("selectedLevel"));
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

            AlertViewPink alert = new AlertViewPink(this, "Something went wrong","Prooblems on the server");
            alert.show();
        }
        // By default play first song
        playSong(0);

        removeViewBorder=new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
//                findViewById(R.id.border).setBackground(null);
                LinearLayout songs = (LinearLayout)findViewById(R.id.songs);
                ImageView image = (ImageView)songs.findViewWithTag("wrong");
                image.setBackground(null);
                image.setTag("");

            }
        };
    }

    public void goBackButton(View view){
        finish();
    }

    public void toTokenShop(View view){
        Intent intent = new Intent();
        intent.setClass(this, TokenShopActivity.class);
        startActivity(intent);
    }

    /**
     * Function to play a song
     * @param songIndex - index of song
     * */
    public void  playSong(int songIndex){
        currentSong = songIndex;
        setSeekBaronPosition(songIndex);
        // Play song
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).get("songPath"));
            mp.prepare();
            mp.start();
            // Displaying Song title
//            String songTitle = songsList.get(songIndex).get("songTitle");
////            songTitleLabel.setText(songTitle);

            // set Progress bar values
//            songProgressBar.setProgress(0);
//            songProgressBar.setMax(50);

            // Updating progress bar
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
            int left = 35 * (currentSong + 1) + 10;
            int pixels = (int) (left * scale + 0.5f);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) seekBar.getLayoutParams();
            params.setMargins(pixels, 0, 0, 0);
            seekBar.setLayoutParams(params);
            playSong(currentSong + 1);

        }else{
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.removeCallbacks(removeViewBorder);
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
        String id = songsList.get(currentSong).get("songTitle");
        if(previousSongTime > 1){
            if (view.getTag().toString().equals(id)){
                view.setBackgroundResource(R.drawable.round_song_big_right);
                userRight = true;
                score += cost;
                yourScore.setText(String.valueOf(score));
            }else{
                view.findViewById(R.id.border).setBackgroundResource(R.drawable.round_song_big_wrong);
                view.findViewById(R.id.border).setTag("wrong");
                userRight = false;
                mHandler.postDelayed(removeViewBorder,2* 1000);
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
        Intent intent = new Intent();
        intent.setClass(this, ResultsActivity.class);
        intent.putExtra("title", barTitle.getText());
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

}
