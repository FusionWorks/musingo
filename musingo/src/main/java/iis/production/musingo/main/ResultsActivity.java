package iis.production.musingo.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.adapter.ResultsCustomListAdapter;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.objects.AlertViewFacebook;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.FacebookManager;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/18/14.
 */
public class ResultsActivity extends Activity {

    ListView list;
    LinearLayout starCollection, score;
    int levelNumber;
    int packageNumber;
    String levelName;
    String packageName;
    String currentScoreStr;
    boolean boostStar;
    boolean beatStar;
    boolean completeStar;

    public static final String APP_PREFERENCES = "settings";
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mSettings.edit().putBoolean("firstPlay",false).commit();
        TextViewPacifico barTitle = (TextViewPacifico)findViewById(R.id.barTitle);
        levelName = getIntent().getStringExtra("levelName");

        starCollection = (LinearLayout) findViewById(R.id.starCollectionView);
        score = (LinearLayout) findViewById(R.id.scoreView);

        if(levelName.length() > 14){
            barTitle.setTextSize(16);
        } else {
            barTitle.setTextSize(21);
        }
        barTitle.setText(levelName);

        Utility.deleteMusingoDir();
        ArrayList<Song> songsList = MainGameActivity.songsWithTime;
//        //<< Temp
//        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.no_album_photo);
//        songsList.add(new Song("backstreet", image, "One Sweet Day", "BY: BACKSTREET BOYS", "21s", "url"));
//        songsList.add(new Song("backstreet", image, "One Sweet Day", "BY: BACKSTREET BOYS", "21s","url"));
//        songsList.add(new Song("backstreet", image, "One Sweet Day", "BY: BACKSTREET BOYS", "21s","url"));
//        songsList.add(new Song("backstreet", image, "One Sweet Day", "BY: BACKSTREET BOYS", "21s","url"));
//        //>>
        ResultsCustomListAdapter songsListAdapter = new ResultsCustomListAdapter(ResultsActivity.this, songsList);
        list = (ListView)findViewById(R.id.listViewSongs);

        list.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });

        LayoutInflater inflater = getLayoutInflater();
//        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.result_header_opened, list, false);
//        list.addHeaderView(header, null, false);

        currentScoreStr = getIntent().getStringExtra("currentScore");
        TextViewArchitects currentScoreTV = (TextViewArchitects) findViewById(R.id.currentScore);
        currentScoreTV.setText(currentScoreStr);

        levelNumber = getIntent().getIntExtra("levelNumber", 0);
        packageNumber = getIntent().getIntExtra("packageNumber", 0);
        packageName = getIntent().getStringExtra("packageName");
        completeStar = getIntent().getBooleanExtra("completeStar", false);
        beatStar = getIntent().getBooleanExtra("beatStar", false);
        boostStar = getIntent().getBooleanExtra("boostStar", false);

        Log.v("Musingo", "Level: " + levelNumber);

        setBestResult();
        setStarCollection();

        list.setAdapter(songsListAdapter);

        facebookLogin();
        Utility.addSelecions(this, R.id.nextButton, R.drawable.selected_next, R.drawable.next_button);

        TextView learnHow = (TextView) findViewById(R.id.learnHow);
        learnHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.goodworldgames.com/about.html"));
                startActivity(browserIntent);
            }
        });

//        list.setOnScrollListener(new AbsListView.OnScrollListener(){
//            int lastVisibleItem;
//            int firstItem;
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
//                firstItem = firstVisibleItem;
//            }
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.v("Musingo", "scrollState : " + scrollState);
//                if(firstItem !=0 && scrollState!=1){
//                    Log.v("Musingo", "scrolling down...");
//                    //score.setVisibility(View.GONE);
//                    //starCollection.setVisibility(View.GONE);
//                    Animation animation = new AlphaAnimation(1.0f, 0.0f);
//                    animation.setDuration(800);
//                    starCollection.startAnimation(animation);
//                    starCollection.setVisibility(View.GONE);
//
//                    score.startAnimation(animation);
//                    score.setVisibility(View.GONE);
//                } else if(firstItem ==0 && scrollState!=2){
//                    Log.v("Musingo", "scrolling up...");
////                    score.setVisibility(View.VISIBLE);
////                    starCollection.setVisibility(View.VISIBLE);
//                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
//                    animation.setDuration(500);
//                    starCollection.startAnimation(animation);
//                    starCollection.setVisibility(View.VISIBLE);
//
//                    score.startAnimation(animation);
//                    score.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        playedGames();
    }

    private void playedGames() {
        int playlistsPlayed = mSettings.getInt("fbLikeShow", 0);
        if(mSettings.getBoolean("facebookLike", true) && playlistsPlayed == 9){
            mSettings.edit().putInt("fbLikeShow", 0).commit();
        } else {
            mSettings.edit().putInt("fbLikeShow", ++playlistsPlayed).commit();
        }
    }

    public void setStarCollection() {
        int starNr = 0;
        if(beatStar){
            starNr = starNr + 1;
        }
        if(completeStar){
            starNr = starNr + 1;
        }
        if(boostStar){
            starNr = starNr + 1;
        }

        TextViewArchitects starCollection = (TextViewArchitects) findViewById(R.id.starCollectionHeader);
        starCollection.setText("STAR COLLECTION : " + starNr + "/3");
    }

    public void starCollection(View v){
        Log.v("Musingo", "starCollection.getVisibility(): " + starCollection.getVisibility() + " v.VISIBLE: " + v.VISIBLE);
        if(starCollection.getVisibility() == 0 ){
            starCollection.setVisibility(View.GONE);
        }
        else {
            starCollection.setVisibility(View.VISIBLE);
        }
    }

    public void score(View v){
        if(score.getVisibility() == 0){
            score.setVisibility(View.GONE);
        }
        else {
            score.setVisibility(View.VISIBLE);
        }
    }

    public void toPartnerActivity(View view){
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, PartnerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

        }
        return false;
    }

    public void setBestResult(){
        TextViewArchitects bestResultTV = (TextViewArchitects) findViewById(R.id.bestScore);
        PlaySongsTable playSongsTable = new PlaySongsTable(this);

        String pn = packageName.replace("'","''");
        String ln = levelName.replace("'","''");

        playSongsTable.insertIntoPlaySongsTable(pn, levelNumber, ln, 0, 0, 0, 0);

        int bestResultInt = playSongsTable.getBestResultByLevel(levelNumber);

        int currentScoreInt = Integer.parseInt(currentScoreStr);

        if(bestResultInt < currentScoreInt){
            bestResultTV.setText(currentScoreStr);
            playSongsTable.updateBestResultByLevel(currentScoreInt, levelNumber);
        }
        else {
            bestResultTV.setText("" + bestResultInt);
        }

        setStar();
//        playSongsTable.updateStarInPlaySongsTable(completeStar, beatStar, boostStar, levelNumber);
    }

    public void setStar(){
        ImageView completeStarImg = (ImageView) findViewById(R.id.completeStar);
        ImageView boostStarImg = (ImageView) findViewById(R.id.boostStar);
        ImageView beatStarImg = (ImageView) findViewById(R.id.beatStar);
        PlaySongsTable playSongsTable = new PlaySongsTable(this);

        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        hash = playSongsTable.getSongByLevelNr(levelNumber);

        if(completeStar || (hash.containsKey("completeStar") && hash.get("completeStar") == 1)){
            completeStarImg.setImageResource(R.drawable.star_complete);
            playSongsTable.updateCompleteStar(true, levelNumber);
        }
        else{
            completeStarImg.setImageResource(R.drawable.star_blank);
        }

        if(boostStar || (hash.containsKey("boostStar") && hash.get("boostStar") == 1)){
            boostStarImg.setImageResource(R.drawable.star_boost);
            playSongsTable.updateBoostStar(true, levelNumber);
        }
        else{
            boostStarImg.setImageResource(R.drawable.star_blank);
        }

        if(beatStar || (hash.containsKey("beatStar") && hash.get("beatStar") == 1)){
            beatStarImg.setImageResource(R.drawable.star_beat);
            playSongsTable.updateBeatStar(true, levelNumber);
        }
        else {
            beatStarImg.setImageResource(R.drawable.star_blank);
        }

        if(beatStar){
            MusingoApp.soundWin();
        } else {
            MusingoApp.soundlose();
        }
    }

    public void facebookLogin(){
        int playlistsPlayed = mSettings.getInt("playlistsPlayed", 0);
        if(!FacebookManager.userFb.isSessionValid() && playlistsPlayed == 4){
            mSettings.edit().putInt("playlistsPlayed", 0).commit();
            AlertViewFacebook dialog = new AlertViewFacebook(this, "Save Yo'self", "SAVE YOUR PROGRESS BY LOGGING INTO FACEBOOK NOW.", ResultsActivity.this);
            dialog.show();
        } else {
            mSettings.edit().putInt("playlistsPlayed", ++playlistsPlayed).commit();
        }
    }

}
