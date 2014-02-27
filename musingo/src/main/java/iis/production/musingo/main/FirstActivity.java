package iis.production.musingo.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.facebook.android.Facebook;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.db.PackageTable;
import iis.production.musingo.db.PlaySongsTable;
import iis.production.musingo.objects.FacebookWebViewClient;
import iis.production.musingo.service.TimerService;
import iis.production.musingo.utility.FacebookManager;

/**
 * Created by AGalkin on 1/18/14.
 */
public class FirstActivity extends Activity {
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    MixpanelAPI mMixpanel;
    WebView webView;
    boolean fbLikeOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        setupMixpanel();
        setupFacebook();
        getMyTokens();

        ImageView roundSettings = (ImageView)findViewById(R.id.settings);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                getResources().getDrawable(R.drawable.selected_settings));
        states.addState(new int[] {android.R.attr.state_focused},
                getResources().getDrawable(R.drawable.selected_settings));
        states.addState(new int[] { },
                getResources().getDrawable(R.drawable.settings));
        roundSettings.setImageDrawable(states);
// <<<temp
        PlaySongsTable playSongsTable = new PlaySongsTable(this);
        playSongsTable.deletePlaySongsTable();

        PackageTable packageTable = new PackageTable(this);
        packageTable.deletePackageTable();
//>>>
        startService(new Intent(this, TimerService.class));

        webView = (WebView) findViewById(R.id.webLike);
        ImageView fbLike = (ImageView) findViewById(R.id.fb_bottom);
        fbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                fbLikeOpen = true;
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new FacebookWebViewClient(FirstActivity.this));
                webView.loadUrl(getString(R.string.like_url));
                webView.requestFocus(View.FOCUS_DOWN);
                webView.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        switch (event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                            case MotionEvent.ACTION_UP:
                                if (!v.hasFocus())
                                {
                                    v.requestFocus();
                                }
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        ImageView twitter = (ImageView) findViewById(R.id.twiter_bottom);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                fbLikeOpen = true;
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new FacebookWebViewClient(FirstActivity.this));
                webView.loadUrl(getString(R.string.twitter_url));
                webView.requestFocus(View.FOCUS_DOWN);
                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                            case MotionEvent.ACTION_UP:
                                if (!v.hasFocus()) {
                                    v.requestFocus();
                                }
                                break;
                        }
                        return false;
                    }
                });
            }
        });
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
        stopService(new Intent(this, TimerService.class));
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

        FacebookManager.mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        String access_token = FacebookManager.mSettings.getString("access_token", null);
        long expires = FacebookManager.mSettings.getLong("access_expires", 0);

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

    public void goBackButton(View view){
        MusingoApp.soundButton();
        if(fbLikeOpen){
            webView.setVisibility(View.GONE);
            fbLikeOpen = false;
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBackButton(null);
        }
//        return super.onKeyDown(keyCode, event);
        return false;
    }
}
