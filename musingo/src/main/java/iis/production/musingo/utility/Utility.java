package iis.production.musingo.utility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
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
        } catch (FileNotFoundException e){
            x = BitmapFactory.decodeResource(activity.getResources(), R.drawable.no_album_photo);
        } catch (IOException e) {
            e.printStackTrace();
            x = BitmapFactory.decodeResource(activity.getResources(), R.drawable.no_album_photo);
        }

        Log.v("Musingo", " height : " + x.getHeight() + " , width : " + x.getWidth());
        if(x.getHeight() > x.getWidth()){
            return  changeBitmapWidth(x);
        } else if(x.getHeight() < x.getWidth()){
            return  changeBitmapHeight(x);
        }
        return x;
    }

    public static Bitmap changeBitmapWidth(Bitmap b) {
       Bitmap bmOverlay = Bitmap.createBitmap(170, 170, b.getConfig());
       Canvas canvas = new Canvas(bmOverlay);
       canvas.drawBitmap(b, (170 - b.getWidth()) / 2, 0, null);
       return bmOverlay;

    }

    public static Bitmap changeBitmapHeight(Bitmap b) {
        Bitmap bmOverlay = Bitmap.createBitmap(170, 170, b.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(b, 0, (170 - b.getHeight()) / 2, null);
        return bmOverlay;

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


    public static void addSelecions(Activity activity, int resource, int selected, int normal){
        ImageView roundSettings = (ImageView)activity.findViewById(resource);
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                activity.getResources().getDrawable(selected));
        states.addState(new int[] {android.R.attr.state_focused},
                activity.getResources().getDrawable(selected));
        states.addState(new int[] { },
                activity.getResources().getDrawable(normal));
        roundSettings.setImageDrawable(states);
    }

    public static void addSelecionsInView(Activity activity, int parentId, int resource, int selected, int normal){
        RelativeLayout layout = (RelativeLayout)activity.findViewById(parentId);
        ImageView roundSettings = (ImageView)layout.findViewById(resource);
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                activity.getResources().getDrawable(selected));
        states.addState(new int[] {android.R.attr.state_focused},
                activity.getResources().getDrawable(selected));
        states.addState(new int[] { },
                activity.getResources().getDrawable(normal));
        roundSettings.setImageDrawable(states);
    }

}
