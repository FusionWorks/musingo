package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.utility.Utility;

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

        Utility.deleteMusingoDir();
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
