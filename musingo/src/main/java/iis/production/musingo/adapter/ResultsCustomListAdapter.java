package iis.production.musingo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewArchitects;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.RoundedCornersDrawable;

/**
 * Created by dima on 1/24/14.
 */
public class ResultsCustomListAdapter extends ArrayAdapter<Song> {
    private final Activity context;
    private final ArrayList<Song> songList;

    public ResultsCustomListAdapter(Activity context, ArrayList<Song> songList) {
        super(context, R.layout.activity_results, songList);
        this.context = context;
        this.songList = songList;
        Log.v("musingo", "adapter song list [0] : " + songList.get(0).getArtistName());
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.result_list_cell, parent, false);

        TextViewPacifico songName = (TextViewPacifico) rowView.findViewById(R.id.songName);
        TextViewArchitects artistName = (TextViewArchitects) rowView.findViewById(R.id.artistName);
        TextViewPacifico timeView = (TextViewPacifico) rowView.findViewById(R.id.time);
        TextViewPacifico noTimeView = (TextViewPacifico) rowView.findViewById(R.id.noTime);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.artistPic);

        Log.v("musingo", "adapter song list : " + songList.get(position).getArtistName());
        songName.setText(songList.get(position).getSongName());
        artistName.setText(songList.get(position).getArtistName());

        final RoundedCornersDrawable drawable = new RoundedCornersDrawable(context.getResources(), songList.get(position).getImage());
        imageView.setImageDrawable(drawable);
        String time = songList.get(position).getTime();
        if (time.equals("-")){
            noTimeView.setText(time);
            noTimeView.setVisibility(View.VISIBLE);
        }else{
            timeView.setText(time+"s");
            timeView.setVisibility(View.VISIBLE);
        }
        return rowView;
    }
}