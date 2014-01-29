package iis.production.musingo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.Integer;import java.lang.Override;import java.lang.String;import iis.production.musingo.R;

/**
 * Created by dima on 1/24/14.
 */
public class ResultsCustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] song;
    private final String[] time;
    private final String[] artist;
    private final Integer[] imageId;
    public ResultsCustomList(Activity context, String[] song, String[] artist, Integer[] imageId, String[] time) {
        super(context, R.layout.songs_list_view, song);
        this.context = context;
        this.song = song;
        this.artist = artist;
        this.imageId = imageId;
        this.time = time;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.songs_list_view, null, true);

        TextView songName = (TextView) rowView.findViewById(R.id.songName);
        TextView artistName = (TextView) rowView.findViewById(R.id.artistName);
        TextView pointText = (TextView) rowView.findViewById(R.id.time);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.artistPic);

        songName.setText(song[position]);
        artistName.setText(artist[position]);
        imageView.setImageResource(imageId[position]);
        pointText.setText(time[position]);
        return rowView;
    }
}