package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;

import iis.production.musingo.MusingoApp;
import iis.production.musingo.R;

/**
 * Created by AGalkin on 1/18/14.
 */
public class PartnerActivity extends Activity {
    public Chartboost chartboost;
    String TAG = "Musingo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);

        this.chartboost = Chartboost.sharedChartboost();
        String appIdChartboost = getString(R.string.appIdChartboost);
        String appSignatureChartboost = getString(R.string.appSignatureChartboost);
        this.chartboost.onCreate(PartnerActivity.this, appIdChartboost, appSignatureChartboost, this.chartBoostDelegate);

        this.chartboost.startSession();
        //Log.v("Musingo", "cache : " + this.chartboost.hasCachedMoreApps());

        this.chartboost.showMoreApps();

    }

    @Override
    protected void onStart(){
        super.onStart();

        this.chartboost.onStart(this);

        //this.chartboost.showInterstitial();
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.chartboost.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.chartboost.onDestroy(this);
    }

    @Override
    public void onBackPressed() {
        if (this.chartboost.onBackPressed())
            return;
        else
            super.onBackPressed();
    }

    private ChartboostDelegate chartBoostDelegate = new ChartboostDelegate() {
        @Override
        public boolean shouldDisplayInterstitial(String location) {
            Log.i(TAG, "SHOULD DISPLAY INTERSTITIAL '" + location + "'?");
            return true;
        }

        @Override
        public boolean shouldRequestInterstitial(String location) {
            Log.i(TAG, "SHOULD REQUEST INSTERSTITIAL '"+location+ "'?");
            return true;
        }

        @Override
        public void didCacheInterstitial(String location) {
            Log.i(TAG, "INTERSTITIAL '"+location+"' CACHED");
        }

        @Override
        public void didFailToLoadInterstitial(String location) {
            Log.i(TAG, "INTERSTITIAL '"+location+"' REQUEST FAILED");
        }

        @Override
        public void didDismissInterstitial(String location) {
            chartboost.cacheInterstitial(location);

            Log.i(TAG, "INTERSTITIAL '"+location+"' DISMISSED");
            Toast.makeText(PartnerActivity.this, "Dismissed Interstitial '"+location+"'",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didCloseInterstitial(String location) {
            Log.i(TAG, "INSTERSTITIAL '"+location+"' CLOSED");
        }

        @Override
        public void didClickInterstitial(String location) {
            Log.i(TAG, "DID CLICK INTERSTITIAL '"+location+"'");
        }

        @Override
        public void didShowInterstitial(String location) {
            Log.i(TAG, "INTERSTITIAL '" + location + "' SHOWN");
        }

        @Override
        public void didFailToLoadUrl(String url) {
            // Show a house ad or do something else when a chartboost interstitial fails to load

            Log.i(TAG, "URL '"+url+"' REQUEST FAILED");
        }

        @Override
        public boolean shouldDisplayLoadingViewForMoreApps() {
            return true;
        }

        @Override
        public boolean shouldRequestMoreApps() {

            return true;
        }

        @Override
        public boolean shouldDisplayMoreApps() {
            Log.i(TAG, "SHOULD DISPLAY MORE APPS?");
            return true;
        }

        @Override
        public void didFailToLoadMoreApps() {
            Log.i(TAG, "MORE APPS REQUEST FAILED");
            Intent intent = new Intent();
            intent.setClass(PartnerActivity.this, LevelSelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        @Override
        public void didCacheMoreApps() {
            Log.i(TAG, "MORE APPS CACHED");
        }

        @Override
        public void didDismissMoreApps() {
            chartboost.cacheMoreApps();
            Log.i(TAG, "MORE APPS DISMISSED");
        }

        @Override
        public void didCloseMoreApps() {
            Log.i(TAG, "MORE APPS CLOSED");
            MusingoApp.soundButton();
            Intent intent = new Intent();
            intent.setClass(PartnerActivity.this, LevelSelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

        @Override
        public void didClickMoreApps() {
            Log.i(TAG, "MORE APPS CLICKED");
        }

        @Override
        public void didShowMoreApps() {
            Log.i(TAG, "MORE APPS SHOWED");
        }

        @Override
        public boolean shouldRequestInterstitialsInFirstSession() {
            return true;
        }
    };
}
