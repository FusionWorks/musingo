package iis.production.musingo.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by dima on 2/22/14.
 */
public class AlertViewFacebookLike  extends AlertDialog {
    Activity activity;

    public WebView webView;

    public final String likeURL = "?fan&";
    public final String unlikeURL = "?unfan&";
    public final String url;
    public static final String APP_PREFERENCES = "settings";
    SharedPreferences mSettings;

    public AlertViewFacebookLike(Activity activity)
    {
        super(activity);
        this.activity = activity;
        url = activity.getString(R.string.like_url);
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alert_like_fb);
        mSettings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        TextViewArchitects notNowFb = (TextViewArchitects) findViewById(R.id.notNowLike);
        notNowFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                dismiss();
            }
        });

        final ImageView fbClick = (ImageView)findViewById(R.id.fb_like_click);
        fbClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusingoApp.soundButton();
                webView = (WebView) activity.findViewById(R.id.webLike);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new FacebookWebViewClient(activity));
                webView.loadUrl(url);
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
                dismiss();
            }
        });
    }
}