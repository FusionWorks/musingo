package iis.production.musingo.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import iis.production.musingo.R;
import iis.production.musingo.main.LevelSelectionActivity;
import iis.production.musingo.objects.Song;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/30/14.
 */
public class ATSongs extends AsyncTask<Void, Void, Void> {
    LevelSelectionActivity activity;
    RelativeLayout loadingView;
    boolean mp3Download;
    ArrayList<Song> songs;
    String url;

    int scoreToBeat;
    String name;
    int cost;

    public ATSongs(LevelSelectionActivity activity, String url, RelativeLayout loadingView, boolean mp3Download, ArrayList<Song> songs){
        super();
        this.activity = activity;
        this.loadingView = loadingView;
        this.mp3Download = mp3Download;
        this.songs = songs;
        this.url = url;

    }

    @Override
    protected Void doInBackground(Void... params) {

        Utility.deleteMusingoDir();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            JSONObject data = new JSONObject(EntityUtils.toString(response.getEntity()));
            Log.v("CL", "data" + data);
            this.name = data.getString("name");
            if (mp3Download){
                this.cost = data.getInt("cost");
                this.scoreToBeat = data.getInt("score_to_beat");
                for(Song song : songs){
                    Log.v("Musingo ","song.id "+song.getId());
                    String id = song.getId();
                    String mp3url = song.getmp3Url();
                    downloadASong(id, mp3url);
                }
            }else{
                JSONArray array = data.getJSONArray("songs");
                for (int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);

                    String id = "";
                    Bitmap image = null;
                    String songName = "";
                    String artistName = "";
                    String time = "";
                    String mp3URL = "";
                    String uuid = "";
                    String imageUrl = "";
                    if(obj.getString("image_url").length()<1){
                        image = BitmapFactory.decodeResource(activity.getResources(),
                                R.drawable.no_album_photo);
                    }else{
                        image = Utility.bitmapFromUrl(activity, obj.getString("image_url"));
                        imageUrl = obj.getString("image_url");
                    }
                    id = obj.getString("id");
                    uuid = obj.getString("uuid");
                    songName = obj.getString("name");
                    artistName = obj.getString("artist");
                    time = "0";
                    mp3URL = obj.getString("mp3_url");
                    Song song = new Song(id, uuid, image, songName, artistName, time, mp3URL, imageUrl);
                    songs.add(song);
                 }
            }




        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void params) {
        loadingView.setVisibility(View.GONE);
        if (mp3Download){
            activity.downloadResultForGame(songs, scoreToBeat, name, cost);
        }else{
            activity.downloadResultForLevels(songs, name);
        }
    }

    @Override
    protected void onPreExecute() {
        loadingView.setVisibility(View.VISIBLE);
    }

    public void downloadASong(String name, String urlString){
        int count;
        try {
            URL url = new URL(urlString);
            URLConnection conection = url.openConnection();
            conection.connect();
            // Get Music file length
            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),10*1024);
            File mkDir = new File(Environment.getExternalStorageDirectory().getPath()+"/Musingo/");
            // have the object build the directory structure, if needed.
            mkDir.mkdirs();
            // Output stream to write file in SD card
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/Musingo/"+name+".mp3");
            Log.v("Musingo", "path "+Environment.getExternalStorageDirectory().getPath()+"/Musingo/"+name+".mp3");
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;

                // Write data to file
                output.write(data, 0, count);
            }
            // Flush output
            output.flush();
            // Close streams
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

}
