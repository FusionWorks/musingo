package iis.production.musingo.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.facebook.android.Facebook;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.db.PackageTable;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.utility.FacebookManager;

/**
 * Created by AGalkin on 1/18/14.
 */
public class FirstActivity extends Activity {
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    MixpanelAPI mMixpanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        setupMixpanel();
        setupFacebook();
        getMyTokens();
//        ImageView playNow = (ImageView)findViewById(R.id.settings);
//        StateListDrawable states = new StateListDrawable();
//        states.addState(new int[] {android.R.attr.state_pressed},
//                getResources().getDrawable(R.drawable.pressed));
//        states.addState(new int[] {android.R.attr.state_focused},
//                getResources().getDrawable(R.drawable.focused));
//        states.addState(new int[] { },
//                getResources().getDrawable(R.drawable.normal));
//        playNow.setImageDrawable(states);
// <<<temp
        PlaySongsTable playSongsTable = new PlaySongsTable(this);
        playSongsTable.deletePlaySongsTable();

        PackageTable packageTable = new PackageTable(this);
        packageTable.deletePackageTable();
//>>>
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

    public void setupMixpanel(){
        String token = getString(R.string.mixPanel_token);
        mMixpanel = MixpanelAPI.getInstance(this, token);
        MusingoApp app = (MusingoApp)getApplication();
        app.loadSongs();
        mixpanelSuperProps();
        app.setMixpanelobj(mMixpanel);
    }

    public void setupFacebook(){
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

    public void getMyTokens(){
        mSettings.edit().putInt("tokens",100).commit();
    }

}
