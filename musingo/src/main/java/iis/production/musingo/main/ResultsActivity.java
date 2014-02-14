package iis.production.musingo.main;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextViewPacifico barTitle = (TextViewPacifico)findViewById(R.id.barTitle);
        levelName = getIntent().getStringExtra("levelName");
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
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.result_header_opened, list, false);
        list.addHeaderView(header, null, false);

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

        list.setAdapter(songsListAdapter);

        facebookLogin();
    }

    public void starCollection(View v){
        starCollection = (LinearLayout) findViewById(R.id.starCollectionView);
        if(starCollection.getVisibility() == View.VISIBLE){
            starCollection.setVisibility(View.GONE);
        }
        else {
            starCollection.setVisibility(View.VISIBLE);
        }
    }

    public void score(View v){
        score = (LinearLayout) findViewById(R.id.scoreView);
        if(score.getVisibility() == View.VISIBLE){
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

        playSongsTable.insertIntoPlaySongsTable(packageName, levelNumber, levelName, 0, 0, 0, 0);

        int bestResultInt = playSongsTable.getBestResult(levelNumber);

        int currentScoreInt = Integer.parseInt(currentScoreStr);

        if(bestResultInt < currentScoreInt){
            bestResultTV.setText(currentScoreStr);
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

        if(completeStar){
            completeStarImg.setImageResource(R.drawable.star_complete);
            playSongsTable.updateCompleteStar(true, levelNumber);
        }
        else{
            completeStarImg.setImageResource(R.drawable.star_blank);
        }

        if(boostStar){
            boostStarImg.setImageResource(R.drawable.star_boost);
            playSongsTable.updateBoostStar(true, levelNumber);
        }
        else{
            boostStarImg.setImageResource(R.drawable.star_blank);
        }

        if(beatStar){
            beatStarImg.setImageResource(R.drawable.star_beat);
            playSongsTable.updateBeatStar(true, levelNumber);
        }
        else {
            beatStarImg.setImageResource(R.drawable.star_blank);
        }
    }

    public void facebookLogin(){
        if(!FacebookManager.userFb.isSessionValid()){
            AlertViewFacebook dialog = new AlertViewFacebook(this, "Save Yo'self", "SAVE YOUT PROGRESS BY LOGGING INTO FACEBOOK NOW.", ResultsActivity.this);
            dialog.show();
        }
    }

}
