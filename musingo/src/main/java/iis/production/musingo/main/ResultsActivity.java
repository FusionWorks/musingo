package iis.production.musingo.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import iis.production.musingo.R;
import iis.production.musingo.main.adapter.ResultsCustomList;

/**
 * Created by AGalkin on 1/18/14.
 */
public class ResultsActivity extends Activity {

    ListView list;
    LinearLayout starCollection, score;
    String[] strSong = {
            "One Sweet Day",
            "It’s Gonna Be Me",
            "Quit Playing Games With ...",
            "When The Lights Go Out",
            "Test",
            "Test",
            "Test"
    };

    String[] strArtist = {
            "BY: BACKSTREET BOYS",
            "BY: 98 DEGREES",
            "BY: BOYS II MEN",
            "BY: LFO",
            "Test",
            "Test",
            "Test"
    };

    String[] strTime = {
            "25s",
            "10s",
            "30s",
            "34s",
            "534s",
            "12s",
            "89s"
    } ;

    Integer[] imageId = {
            R.drawable.bsb,
            R.drawable.lfo,
            R.drawable.degrees,
            R.drawable.five,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ResultsCustomList songsList = new ResultsCustomList(ResultsActivity.this, strSong, strArtist, imageId, strTime);
        list = (ListView)findViewById(R.id.listViewSongs);

        list.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.result_view, list, false);
        list.addHeaderView(header, null, false);

        list.setAdapter(songsList);
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
