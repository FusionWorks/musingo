package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import iis.production.musingo.R;
import iis.production.musingo.main.more.TokenShopActivity;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.SongsManager;
import iis.production.musingo.utility.Utility;

public class MainGameActivity extends Activity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

    // Views vars
    TextView coins;
    TextViewArchitects scoreToBeat;
    TextViewArchitects yourScore;
    SeekBar songProgressBar;
    TextViewPacifico currentSong;

    //Media Player vars
    MediaPlayer mp;
    SongsManager songManager;
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private Handler mHandler = new Handler();
    private boolean isRepeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        RelativeLayout lay = (RelativeLayout)findViewById(R.id.song1);
        currentSong = (TextViewPacifico)lay.findViewById(R.id.title);

//        songProgressBar = (SeekBar)findViewById(R.id.songProgressBar);

        // Mediaplayer
        mp = new MediaPlayer();
        songManager = new SongsManager();

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        // Getting all songs list
        songsList = songManager.getPlayList();

        // By default play first song
        playSong(0);
        isRepeat = true;
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
            songProgressBar.setProgress(0);
            songProgressBar.setMax(50);

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
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying time completed playing
            currentSong.setText(""+ Utility.milliSecondsToTimer(currentDuration));

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
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
        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(0);
        }
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
}
