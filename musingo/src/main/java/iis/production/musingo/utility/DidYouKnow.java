package iis.production.musingo.utility;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import iis.production.musingo.R;
import iis.production.musingo.objects.TextViewArchitects;

/**
 * Created by AGalkin on 2/6/14.
 */
public class DidYouKnow {

    public static void random(TextViewArchitects view, Activity activity){
        Field[] fields = R.string.class.getFields();
        ArrayList<String> allStrings = new ArrayList<String>();
        for (int  i =0; i < fields.length; i++) {
            if(fields[i].getName().contains("did_you_know_")){
                allStrings.add(fields[i].getName());
                Log.v("Musingo", "string " + fields[i].getName());
            }
        }

        Random r = new Random();
        int random = r.nextInt(allStrings.size());
        String text = allStrings.get(random);

        int strId = activity.getResources().getIdentifier(text, "string", activity.getPackageName());
        String strValue = activity.getString(strId);

        view.setText(strValue);
//        int l = strValue.length();
//        if(l < 50)
//            view.setTextSize(11);
//        else if(l > 50 && l < 80)
//            view.setTextSize(9);
//        else if(l > 80 && l < 150)
//            view.setTextSize(8);
    }
}
