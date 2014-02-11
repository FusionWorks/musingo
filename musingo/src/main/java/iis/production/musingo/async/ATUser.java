package iis.production.musingo.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dima on 2/11/14.
 */
public class ATUser extends AsyncTask<Void, Void, Void>{
    private JSONObject data;
    private String token;
    private RelativeLayout loadingView;

    public ATUser (JSONObject data, String token, RelativeLayout loadingView){
        this.data = data;
        this.token = token;
        this.loadingView = loadingView;
    }

    @Override
    protected void onPostExecute(Void params) {
        //loadingView.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        postData();
        return null;
    }

    @Override
    protected void onPreExecute() {
        //loadingView.setVisibility(View.VISIBLE);
    }

    public void postData(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://musingo.goodworldgames.com/user?token=" + token);

        String result = "error";

        try {
            StringEntity se = null;
            se = new StringEntity(data.toString());

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.getParams().setParameter("Content-type", "application/json");

            System.out.print(data);
            HttpResponse response = httpclient.execute(httppost);

            if(response != null)
            {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                result = sb.toString();
            }
        }
        catch (ClientProtocolException e) {  Log.v("Musigo", "ClientProtocolException:  " + e); }
        catch (IOException e) {Log.v("Musigo", "IOException:  " + e);}

        Log.v("Musigo", "post:  " + result);
        Log.v("Musigo", "token:  " + token);
    }
}
