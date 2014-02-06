package iis.production.musingo.main;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.adapter.ResultsCustomListAdapter;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/18/14.
 */
public class ResultsActivity extends Activity {

    ListView list;
    LinearLayout starCollection, score;
    int levelNumber;
    String currentScoreStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextViewPacifico barTitle = (TextViewPacifico)findViewById(R.id.barTitle);
        barTitle.setText(getIntent().getStringExtra("levelName"));

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

        setBestResult();

        list.setAdapter(songsListAdapter);
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

        try{
            playSongsTable.insertIntoPlaySongsTable(1, "qwe", levelNumber, "asd", 0, 0, 0, 0);
        }
        catch (Exception e){}

        int bestResultInt = playSongsTable.getBestResult(levelNumber);

        int currentScoreInt = Integer.parseInt(currentScoreStr);

        if(bestResultInt < currentScoreInt){
            playSongsTable.updateBestResultInPlaySongsTable(currentScoreInt, levelNumber);
            bestResultTV.setText(currentScoreStr);
        }
        else {
            bestResultTV.setText("" + bestResultInt);
        }

    }

}
