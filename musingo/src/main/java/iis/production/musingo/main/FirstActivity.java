package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.android.Facebook;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.utility.FacebookManager;

/**
 * Created by AGalkin on 1/18/14.
 */
public class FirstActivity extends Activity {
    MixpanelAPI mMixpanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        String token = getString(R.string.mixPanel_token);
        mMixpanel = MixpanelAPI.getInstance(this, token);
        MusingoApp app = (MusingoApp)getApplication();
        app.loadSongs();
        mixpanelSuperProps();
        app.setMixpanelobj(mMixpanel);
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
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, LevelSelectionActivity.class);
        startActivity(intent);
    }

    public void goToSettings(View view){
        view.playSoundEffect(0);
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        MusingoApp app = (MusingoApp)getApplication();
        app.getMixpanelobj().flush();
        super.onDestroy();
    }

    public void mixpanelSuperProps(){
        JSONObject props = new JSONObject();
        try {
            props.put("Operating system", "Android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMixpanel.registerSuperProperties(props);
    }

}
