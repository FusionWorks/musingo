package iis.production.musingo.objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by dima on 2/22/14.
 */
public class AlertViewFacebookLike  extends AlertDialog {
    Activity activity;
    //	String url = "http://m.facebook.com/intelkorea";
    //168356489891715

    public WebView webView;

    public final String likeURL = "?fan&";
    public final String unlikeURL = "?unfan&";
    public final String url = "http://m.facebook.com/168356489891715";
    public static final String APP_PREFERENCES = "settings";
    SharedPreferences mSettings;

    public AlertViewFacebookLike(Activity activity)
    {
        super(activity);
        this.activity = activity;
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
                webView.setWebViewClient(new FacebookWebViewClient());
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

    class FacebookWebViewClient extends WebViewClient {

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

            boolean following = url.indexOf(likeURL) > -1;
            boolean unFollowing = url.indexOf(unlikeURL) > -1;

            if (following) {
                mSettings.edit().putBoolean("facebookLike",false).commit();
                Toast.makeText(activity, "You have just selected 'like' ", Toast.LENGTH_LONG).show();
            } else if (unFollowing) {
                mSettings.edit().putBoolean("facebookLike",false).commit();
                Toast.makeText(activity, "You have just selected 'unlike' ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            super.onUnhandledKeyEvent(view, event);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			return super.shouldOverrideUrlLoading(view, url);
            return false;

        }

    }

}