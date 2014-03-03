package iis.production.musingo.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;
import iis.production.musingo.main.more.AboutActivity;
import iis.production.musingo.main.more.HowToActivity;
import iis.production.musingo.main.more.TokenShopActivity;
import iis.production.musingo.objects.AlertViewFacebook;
import iis.production.musingo.objects.CustomWebViewClient;
import iis.production.musingo.utility.FacebookManager;
import iis.production.musingo.utility.Utility;

/**
 * Created by AGalkin on 1/18/14.
 */
public class SettingsActivity extends Activity {
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";
    WebView webView;
    boolean fbLikeOpen = false;
    ImageView fbImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        RelativeLayout sounds = (RelativeLayout)findViewById(R.id.sounds);
        ImageView image = (ImageView)sounds.findViewById(R.id.imageRightCheck);
        if(mSettings.getBoolean("sounds", true)){
            image.setImageResource(R.drawable.yes);
            image.setTag("YES");
        }else{
            image.setImageResource(R.drawable.no);
            image.setTag("NO");
        }

        webView = (WebView) findViewById(R.id.webLike);
        RelativeLayout logged = (RelativeLayout)findViewById(R.id.logedInLayout);
        fbImage = (ImageView)logged.findViewById(R.id.imageRightCheck);
        if(FacebookManager.userFb.isSessionValid() || !mSettings.getBoolean("facebookLike", true)){
            fbImage.setImageResource(R.drawable.yes);
        }else{
            fbImage.setImageResource(R.drawable.no);
            fbImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    faceBookLike();
                }
            });
        }

        Utility.addSelecions(this, R.id.backButton, R.drawable.selected_back, R.drawable.back_button);
        Utility.addSelecions(this, R.id.imageRight, R.drawable.selected_arrow_right, R.drawable.arrow_right);

        RelativeLayout fbLike = (RelativeLayout) findViewById(R.id.fbLike);
        fbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceBookLike();
            }
        });

        RelativeLayout twitter = (RelativeLayout) findViewById(R.id.twiter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                fbLikeOpen = true;
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new CustomWebViewClient(SettingsActivity.this, webView));
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

    private void faceBookLike() {
        MusingoApp.soundButton();
        fbLikeOpen = true;
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient(SettingsActivity.this, webView));
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

    public void goBackButton(View view){
        MusingoApp.soundButton();
        if(fbLikeOpen){
            webView.setVisibility(View.GONE);
            if(mSettings.getBoolean("facebookLike", true)){
                fbImage.setImageResource(R.drawable.no);
            }
            fbLikeOpen = false;
        } else {
            finish();
        }
    }

    public void goToHowTo(View view){
        view.findViewById(R.id.imageRight).performClick();
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, HowToActivity.class);
        startActivity(intent);
    }

    public void toTellFriend(View view){
        view.findViewById(R.id.imageRight).performClick();
        MusingoApp.soundButton();

        if(!FacebookManager.userFb.isSessionValid()){
            AlertViewFacebook dialog = new AlertViewFacebook(this, "login", "", SettingsActivity.this);
            dialog.show();
        }
        else{
            //FacebookManager.PostFb(SettingsActivity.this);
        }
    }

    public void toAboutUs(View view){
        view.findViewById(R.id.imageRight).performClick();
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        intent.putExtra("mode", "us");
        startActivity(intent);
    }

    public void toAboutPartners(View view){
        view.findViewById(R.id.imageRight).performClick();
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        intent.putExtra("mode", "partners");
        startActivity(intent);
    }

    public void toTokenShop(View view){
        view.findViewById(R.id.imageRight).performClick();
        MusingoApp.soundButton();
        Intent intent = new Intent();
        intent.setClass(this, TokenShopActivity.class);
        startActivity(intent);

    }

    public void toSounds(View view){
        MusingoApp.soundButton();
        ImageView status = (ImageView)view.findViewById(R.id.imageRightCheck);
        if(status.getTag().toString().equals("YES")){
            MusingoApp.volume = 0.1f;
            MusingoApp.soundButton();
            status.setImageResource(R.drawable.no);
            status.setTag("NO");
            mSettings.edit().putBoolean("sounds",false).commit();
        }else{
            MusingoApp.volume = 0.9f;
            status.setImageResource(R.drawable.yes);
            status.setTag("YES");
            mSettings.edit().putBoolean("sounds",true).commit();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBackButton(null);
        }
//        return super.onKeyDown(keyCode, event);
        return false;
    }

}
