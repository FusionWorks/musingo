package iis.production.musingo.objects;

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
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final String[] point;
    private final Integer[] imageId;
    public CustomList(Activity context, String[] web, Integer[] imageId, String[] point) {
        super(context, R.layout.songs_list_view, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.point = point;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.songs_list_view, null, true);

        TextView songName = (TextView) rowView.findViewById(R.id.textViewSongName);
        TextView pointText = (TextView) rowView.findViewById(R.id.textViewTime);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.artistPic);

        songName.setText(web[position]);
        imageView.setImageResource(imageId[position]);
        pointText.setText(point[position]);
        return rowView;
    }
}