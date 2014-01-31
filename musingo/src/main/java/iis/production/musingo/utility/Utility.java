package iis.production.musingo.utility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import iis.production.musingo.R;

/**
 * Created by AGalkin on 1/24/14.
 */
public class Utility {

    public static String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString =  secondsString;

        // return timer string
        return finalTimerString;
    }

    public static Drawable drawableFromUrl(String url, Activity activity) throws IOException {
        if(url.length()>0){
            Bitmap x;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            x = BitmapFactory.decodeStream(input);
            return new BitmapDrawable(activity.getApplicationContext().getResources(), x);
        }else{
            return null;
        }
    }

    public static Bitmap bitmapFromUrl(Activity activity, String url){
        Bitmap x;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream input = null;
        try {
            input = connection.getInputStream();
            x = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            x = BitmapFactory.decodeResource(activity.getResources(), R.drawable.no_album_photo);
        }

        return x;
    }

    public static void setBackgroundBySDK(View view, Drawable bg){
        int sdkVersion = android.os.Build.VERSION.SDK_INT;

        if(sdkVersion < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(bg);
        } else {
            view.setBackground(bg);
        }
    }

    public static boolean deleteMusingoDir() {
        File path = new File(Environment.getExternalStorageDirectory().getPath()+"/Musingo/");
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                files[i].delete();
            }
        }
        return( path.delete() );
    }
}
