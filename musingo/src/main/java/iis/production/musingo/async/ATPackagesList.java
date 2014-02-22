package iis.production.musingo.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import iis.production.musingo.main.LevelSelectionActivity;
import iis.production.musingo.objects.Package;
import iis.production.musingo.objects.TextViewPacifico;
import iis.production.musingo.utility.Utility;

/**
 * Created by dima on 2/21/14.
 */
public class ATPackagesList  extends AsyncTask<Void, Void, Void> {
    LevelSelectionActivity activity;
    RelativeLayout loadingView;
    ArrayList<Package> packages;
    String url;
    Boolean timeOut = false;
    int increment = 0;
    ProgressBar pb;

    TextViewPacifico songsLoaded;
    LinearLayout linearLayout;

    public ATPackagesList(LevelSelectionActivity activity, String url, RelativeLayout loadingView, Drawable drawable){
        super();
        this.activity = activity;
        this.loadingView = loadingView;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        packages = new ArrayList<Package>();
        Utility.deleteMusingoDir();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            JSONObject data = new JSONObject(EntityUtils.toString(response.getEntity()));
            final JSONArray array = data.getJSONArray("package_list");
            Log.v("Packages", "data : " + data);
            final int packagesNumber = array.length();
            for(int i=0; i < packagesNumber; i++){
                JSONObject obj = (JSONObject)array.get(i);
                Log.v("Packages","package " + obj.getString("package_name"));
                int star = obj.getInt("stars_to_unlock");
                String name = obj.getString("package_name");
                int id = obj.getInt("package_id");

                Package aPackage = new Package(id, name, star);
                packages.add(aPackage);

//                onProgressUpdate(++increment);
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        songsLoaded.setText(increment + "/" + packagesNumber);
//                    }
//                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        }catch (ConnectTimeoutException e){
            timeOut = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    protected void onProgressUpdate(int... progress) {
//        pb.setProgress(progress[0]);
//    }

    @Override
    protected void onPostExecute(Void params) {
//        linearLayout.removeView(pb);
//        linearLayout.setVisibility(View.GONE);
        if(timeOut){
            activity.changePackage();
        }else{
            activity.getPackagesList(packages);
        }
    }

    @Override
    protected void onPreExecute() {

//        songsLoaded = (TextViewPacifico) activity.findViewById(R.id.songsLoaded);
//        songsLoaded.setText("0");
//        linearLayout = (LinearLayout) activity.findViewById(R.id.progressBarHandler);
//        linearLayout.setVisibility(View.VISIBLE);
//
//        pb = new ProgressBar(activity, null, android.R.attr.progressBarStyleHorizontal);
//        Drawable d = new CustomProgressBar();
//        pb.setProgressDrawable(d);
//        pb.setProgress(0);
//        pb.setMax(9);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(5, 0, 2, 20);
//        pb.setLayoutParams(params);
//
//        linearLayout.addView(pb);
    }
}