package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by AGalkin on 1/18/14.
 */
public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

<<<<<<< HEAD
        MixpanelAPI mMixpanel = MixpanelAPI.getInstance(this, "YOUR_API_TOKEN");
        MusingoApp app = (MusingoApp)getApplication();
        app.setMixpanelAPI(mMixpanel);
//<< TEMP, to bn removed
        Button button = (Button) findViewById(R.id.buttonResults);
        button.setOnClickListener(new View.OnClickListener() {
=======
        Button buttonResults = (Button) findViewById(R.id.buttonResults);
        buttonResults.setOnClickListener(new View.OnClickListener() {
>>>>>>> 8bce1f3fa32693f956a16f82962fe2d7ed10af66
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(FirstActivity.this, ResultsActivity.class);
                FirstActivity.this.startActivity(activityChangeIntent);
            }
        });
<<<<<<< HEAD
//>>
=======

        Button buttonPartner = (Button) findViewById(R.id.buttonPartner);
        buttonPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(FirstActivity.this, PartnerActivity.class);
                FirstActivity.this.startActivity(activityChangeIntent);
            }
        });
>>>>>>> 8bce1f3fa32693f956a16f82962fe2d7ed10af66
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
