package iis.production.musingo.utility;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import iis.production.musingo.async.ATUser;

/**
 * Created by dima on 1/30/14.
 */
public class FacebookManager {
   public static Facebook userFb;
   public static SharedPreferences sharedPreferences;

   public FacebookManager(){

   }

   public static void SessionFb(Activity activity, final RelativeLayout loadingView){
       if(!userFb.isSessionValid()){
           userFb.authorize(activity, new String[] {"email"}, new Facebook.DialogListener() {
               @Override
               public void onComplete(Bundle values) {
                   String userId = "";
                   String userName = "";
                   try {
                       String jsonUser = userFb.request("me");
                       JSONObject obj = null;
                       obj = Util.parseJson(jsonUser);
                       userId = obj.optString("id");
                       userName = obj.optString("name");
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("access_token", userFb.getAccessToken());
                   editor.putLong("access_expires", userFb.getAccessExpires());
                   editor.putString("user_id", userId);
                   editor.putString("user_name", userName);
                   editor.commit();

                   long unixTime = System.currentTimeMillis();

                   JSONObject data = new JSONObject();
                   try {
                       data.put("id", "facebook_" + userId);
                       data.put("service", "Facebook");
                       data.put("service_id", userId);
                       data.put("name", userName);
                       data.put("tokens", 100);
                       data.put("sounds", true);
                       data.put("level", 1);
                       data.put("type", "user");
                       data.put("apns", "");
                       data.put("apids", "");
                       data.put("friends", "");
                       data.put("playlist_categories", "");
                       data.put("receipt", "");
                       data.put("isFirstGame", true);
                       data.put("activePromo", true);
                       data.put("lastPlay", unixTime);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

                   ATUser atUser = new ATUser(data, userFb.getAccessToken(), loadingView);
                   atUser.execute();
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
