package iis.production.musingo.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import iis.production.musingo.objects.CustomProgressBar;
import iis.production.musingo.objects.Song;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/30/14.
 */
public class ATSongs extends AsyncTask<Void, Void, Void> {
    LevelSelectionActivity activity;
    RelativeLayout loadingView;
    ArrayList<Song> songs;
    String url;

    int scoreToBeat;
    String name;
    int cost;

    ProgressBar pb;
    int increment = 0;

    TextViewPacifico songsLoaded;
    LinearLayout linearLayout;

    public ATSongs(LevelSelectionActivity activity, String url, RelativeLayout loadingView){
        super();
        this.activity = activity;
        this.loadingView = loadingView;
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
            this.cost = data.getInt("cost");
            this.scoreToBeat = data.getInt("score_to_beat");
            JSONArray array = data.getJSONArray("songs");
            for (int i=0; i<array.length(); i++){
                if(i>8)
                    break;
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
                time = "";
                mp3URL = obj.getString("mp3_url");
                Song song = new Song(id, uuid, image, songName, artistName, time, mp3URL, imageUrl);
                songs.add(song);
                downloadASong(id, mp3URL);

                onProgressUpdate(++increment);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        songsLoaded.setText(increment + "/9");
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(int... progress) {
        pb.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Void params) {
//        loadingView.setVisibility(View.GONE);
        linearLayout.removeView(pb);
        linearLayout.setVisibility(View.GONE);
        activity.downloadResultForGame(songs, name, cost);

    }

    @Override
    protected void onPreExecute() {
        songs = new ArrayList<Song>();
//        loadingView.setVisibility(View.VISIBLE);
        songsLoaded = (TextViewPacifico) activity.findViewById(R.id.songsLoaded);
        songsLoaded.setText("0/9");
        linearLayout = (LinearLayout) activity.findViewById(R.id.progressBarHandler);
        linearLayout.setVisibility(View.VISIBLE);

        pb = new ProgressBar(activity, null, android.R.attr.progressBarStyleHorizontal);
        Drawable d = new CustomProgressBar();
        pb.setProgressDrawable(d);
        pb.setProgress(0);
        pb.setMax(9);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 2, 20);
        pb.setLayoutParams(params);

        linearLayout.addView(pb);
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
