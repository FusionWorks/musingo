package iis.production.musingo.objects;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import iis.production.musingo.R;

/**
 * Created by dima on 2/27/14.
 */
public class CustomWebViewClient extends android.webkit.WebViewClient {
    public final String likeURL = "?fan&";
    public final String unlikeURL = "?unfan&";
    public final String followUrl = "follow";
    public final String unfollowUrl = "unfollow";
    public String url = "";
    public static final String APP_PREFERENCES = "settings";
    SharedPreferences mSettings;
    public Activity activity;
    public WebView webView;

        public CustomWebViewClient(Activity activity, WebView webView){
            this.activity = activity;
            this.webView = webView;
            mSettings = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            url = activity.getString(R.string.like_url);
        }

        @Override
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            super.onFormResubmission(view, dontResend, resend);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

            Log.v("Musingo", "twitter url:" + url);
            boolean like = url.indexOf(likeURL) > -1;
            boolean unLike = url.indexOf(unlikeURL) > -1;
            boolean follow = url.indexOf(followUrl) > -1;
            boolean unfollow = url.indexOf(unfollowUrl) > -1;

            if (like) {
                mSettings.edit().putBoolean("facebookLike",false).commit();
                Toast.makeText(activity, "You have just selected 'like' ", Toast.LENGTH_LONG).show();
                webView.setVisibility(View.GONE);
            } else if (unLike) {
                mSettings.edit().putBoolean("facebookLike",true).commit();
                Toast.makeText(activity, "You have just selected 'unlike' ", Toast.LENGTH_LONG).show();
            }

            if (follow){
                mSettings.edit().putBoolean("twitterFollow",false).commit();
                Toast.makeText(activity, "You have just selected 'follow' ", Toast.LENGTH_LONG).show();
                webView.setVisibility(View.GONE);
            } else if (unfollow){
                mSettings.edit().putBoolean("twitterFollow",true).commit();
                Toast.makeText(activity, "You have just selected 'unfollow' ", Toast.LENGTH_LONG).show();
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
