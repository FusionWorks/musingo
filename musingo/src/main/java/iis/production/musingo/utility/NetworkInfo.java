package iis.production.musingo.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by dima on 2/7/14.
 */
public class NetworkInfo {

    Context context;
    public NetworkInfo(Context context){
        this.context = context;
    }

    public boolean isConnect(){
        ConnectivityManager cm ;
        android.net.NetworkInfo info = null;

        try {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = cm.getActiveNetworkInfo();
            Log.v("Musingo", String.format("%s: %s", info.getTypeName(), info.isConnected()));

        } catch (Exception e) {
            Log.v("Musingo", "connectivity : " + e.toString());
        }

        if(info != null){
            return info.isConnected();
        }else{
            return false;
        }
    }
}
