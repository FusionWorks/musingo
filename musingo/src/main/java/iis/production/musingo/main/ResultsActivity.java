package iis.production.musingo.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.adapter.ResultsCustomListAdapter;
import iis.production.musingo.objects.Song;

/**
 * Created by AGalkin on 1/18/14.
 */
public class ResultsActivity extends Activity {

    ListView list;
    LinearLayout starCollection, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayList<Song> songsList = new ArrayList<Song>();
        songsList.add(new Song(R.drawable.bsb, "One Sweet Day", "BY: BACKSTREET BOYS", "21s"));
        songsList.add(new Song(R.drawable.lfo, "One Sweet Day", "BY: BACKSTREET BOYS", "21s"));
        songsList.add(new Song(R.drawable.lfo, "One Sweet Day", "BY: BACKSTREET BOYS", "21s"));
        songsList.add(new Song(R.drawable.lfo, "One Sweet Day", "BY: BACKSTREET BOYS", "21s"));

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
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.result_view, list, false);
        list.addHeaderView(header, null, false);

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
}
