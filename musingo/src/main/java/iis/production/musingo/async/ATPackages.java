package iis.production.musingo.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.main.LevelSelectionActivity;
import iis.production.musingo.objects.Playlist;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 2/10/14.
 */
public class ATPackages extends AsyncTask<Void, Void, Void> {
    LevelSelectionActivity activity;
    RelativeLayout loadingView;
    ArrayList<Playlist> playlists;
    String url;

    int starsToUnlock;
    String packageName;

    public ATPackages(LevelSelectionActivity activity, String url, RelativeLayout loadingView){
        super();
        this.activity = activity;
        this.loadingView = loadingView;
        this.url = url;

    }

    @Override
    protected Void doInBackground(Void... params) {
        playlists = new ArrayList<Playlist>();
        Utility.deleteMusingoDir();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            JSONObject data = new JSONObject(EntityUtils.toString(response.getEntity()));
            starsToUnlock = data.getInt("stars_to_unlock");
            packageName = data.getString("package_name");
            JSONArray array = data.getJSONArray("playlists");
            Log.v("CL", "data" + data);
            for(int i=0; i < array.length(); i++){
                JSONObject obj = (JSONObject)array.get(i);
                Log.v("Musingo","playlist " + obj.getString("playlist_name"));
                String name = obj.getString("playlist_name");
                String genre = obj.getString("genre");
                int listNumber = obj.getInt("list_number");
                int scoreToBeat = obj.getInt("score_to_beat");
                int order = obj.getInt("order");
                Bitmap image;
                if(obj.getString("album_image").length()<1){
                    image = BitmapFactory.decodeResource(activity.getResources(),
                            R.drawable.no_album_photo);
                }else{
                    image = Utility.bitmapFromUrl(activity, obj.getString("album_image"));
                }

                Playlist playlist = new Playlist(name, genre, listNumber, scoreToBeat, order, image);
                playlists.add(playlist);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void params) {
        loadingView.setVisibility(View.GONE);
        activity.downloadResultForLevels(playlists, packageName, starsToUnlock);

    }

    @Override
    protected void onPreExecute() {
        loadingView.setVisibility(View.VISIBLE);
    }
}
