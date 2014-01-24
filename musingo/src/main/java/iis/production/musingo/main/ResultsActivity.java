package iis.production.musingo.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.objects.CustomList;
import iis.production.musingo.objects.ExpListAdapter;

/**
 * Created by AGalkin on 1/18/14.
 */
public class ResultsActivity extends Activity {

    ListView list;
    String[] strArray = {
            "Test",
            "Test",
            "Test",
            "Test",
            "Test",
            "Test",
            "Test"
    };

    String[] pointArray = {
            "68",
            "123",
            "98",
            "34",
            "534",
            "12",
            "89"
    } ;

    Integer[] imageId = {
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ExpandableListView starCollection = (ExpandableListView)findViewById(R.id.starCollection);
        ExpandableListView score = (ExpandableListView)findViewById(R.id.score);


        ArrayList<ArrayList<String>> starCollectionGroup = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> scoreGroup = new ArrayList<ArrayList<String>>();

        ArrayList<String> starCollectionChildren = new ArrayList<String>();
        ArrayList<String> scoreChildren = new ArrayList<String>();

        starCollectionChildren.add("");
        starCollectionGroup.add(starCollectionChildren);

        scoreChildren.add("");
        scoreGroup.add(scoreChildren);

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), starCollectionGroup, R.layout.star_collection_view, "STAR COLLECTION");
        starCollection.setAdapter(adapter);

        adapter = new ExpListAdapter(getApplicationContext(), scoreGroup, R.layout.score_view, "SCORE");
        score.setAdapter(adapter);

        CustomList songsList = new CustomList(ResultsActivity.this, strArray, imageId, pointArray);
        list = (ListView)findViewById(R.id.listViewSongs);
        list.setAdapter(songsList);
        //list.setAdapter(new ArrayAdapter<String>(ResultsActivity.this, android.R.layout.simple_expandable_list_item_1, web));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ResultsActivity.this, "You Clicked at " + strArray[+position], Toast.LENGTH_SHORT).show();
            }
        });

    }
}
