package iis.production.musingo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.objects.Package;
import iis.production.musingo.objects.TextViewPacifico;

/**
 * Created by dima on 2/21/14.
 */
public class PackagesListAdapter  extends ArrayAdapter<Package> {
    private final Activity context;
    private final ArrayList<Package> packagesList;

    public PackagesListAdapter(Activity context, ArrayList<Package> packagesList) {
        super(context, R.layout.package_list_cell, packagesList);
        this.context = context;
        this.packagesList = packagesList;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.package_list_cell, parent, false);

        TextViewPacifico name = (TextViewPacifico) rowView.findViewById(R.id.packageName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.lockImage);

        name.setText("#" + packagesList.get(position).getId() + " " + packagesList.get(position).getName());

        PlaySongsTable table = new PlaySongsTable(context);
        int starExist = table.getStarBeat();
        int starNeed = packagesList.get(position).getStar();

        if (starExist >= starNeed){
            imageView.setVisibility(View.INVISIBLE);
        }else{
            imageView.setVisibility(View.VISIBLE);
        }
        return rowView;
    }
}