package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.android.Facebook;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.utility.FacebookManager;

/**
 * Created by AGalkin on 1/18/14.
 */
public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        MixpanelAPI mMixpanel = MixpanelAPI.getInstance(this, "YOUR_API_TOKEN");
        MusingoApp app = (MusingoApp)getApplication();
        app.setMixpanelAPI(mMixpanel);

        String appIdFacebook = getString(R.string.appIdFacebook);
        FacebookManager.userFb = new Facebook(appIdFacebook);

        FacebookManager.sharedPreferences = getPreferences(MODE_PRIVATE);

        String access_token = FacebookManager.sharedPreferences.getString("access_token", null);
        long expires = FacebookManager.sharedPreferences.getLong("access_expires", 0);

        if(access_token != null){
            FacebookManager.userFb.setAccessToken(access_token);
        }
        if(expires != 0){
            FacebookManager.userFb.setAccessExpires(expires);
        }
    }

    public void goToGame(View view){
        Intent intent = new Intent();
        intent.setClass(this, LevelSelectionActivity.class);
        startActivity(intent);
    }

    public void goToSettings(View view){
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
    }

}
