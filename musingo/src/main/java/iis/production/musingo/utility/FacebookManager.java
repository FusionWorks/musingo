package iis.production.musingo.utility;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

/**
 * Created by dima on 1/30/14.
 */
public class FacebookManager {
   public static Facebook userFb;
   public static SharedPreferences sharedPreferences;

   public FacebookManager(){

   }

   public static void SessionFb(Activity activity){
       if(!userFb.isSessionValid()){
           userFb.authorize(activity, new String[] {"email"}, new Facebook.DialogListener() {
               @Override
               public void onComplete(Bundle values) {
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("access_token", userFb.getAccessToken());
                   editor.putLong("access_expires", userFb.getAccessExpires());
                   editor.commit();
               }

               @Override
               public void onFacebookError(FacebookError e) {

               }

               @Override
               public void onError(DialogError e) {

               }

               @Override
               public void onCancel() {

               }
           });
       }
   }

   public static void PostFb(Activity activity, Bundle params, Facebook.DialogListener dialogListener){
       FacebookManager.userFb.dialog(activity, "feed", params, dialogListener);
   }
}
