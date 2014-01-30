package iis.production.musingo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import iis.production.musingo.R;

import com.chartboost.sdk.*;
import com.chartboost.sdk.ChartboostDelegate;

/**
 * Created by AGalkin on 1/18/14.
 */
public class PartnerActivity extends Activity {
    private Chartboost chartboost;
    String TAG = "Musingo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);

        this.chartboost = Chartboost.sharedChartboost();
        String appIdChartboost = getString(R.string.appIdChartboost);
        String appSignatureChartboost = getString(R.string.appSignatureChartboost);
        this.chartboost.onCreate(PartnerActivity.this, appIdChartboost, appSignatureChartboost, this.chartBoostDelegate);

    }

    @Override
    protected void onStart(){
        super.onStart();

        this.chartboost.onStart(this);
        this.chartboost.startSession();
        this.chartboost.showMoreApps();
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
            Toast.makeText(PartnerActivity.this, "Interstitial '" + location + "' Load Failed",
                    Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PartnerActivity.this, "Closed Interstitial '"+location+"'",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didClickInterstitial(String location) {
            Log.i(TAG, "DID CLICK INTERSTITIAL '"+location+"'");
            Toast.makeText(PartnerActivity.this, "Clicked Interstitial '"+location+"'",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didShowInterstitial(String location) {
            Log.i(TAG, "INTERSTITIAL '" + location + "' SHOWN");
        }

        @Override
        public void didFailToLoadUrl(String url) {
            // Show a house ad or do something else when a chartboost interstitial fails to load

            Log.i(TAG, "URL '"+url+"' REQUEST FAILED");
            Toast.makeText(PartnerActivity.this, "URL '"+url+"' Load Failed",
                    Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PartnerActivity.this, "More Apps Load Failed",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didCacheMoreApps() {
            Log.i(TAG, "MORE APPS CACHED");
        }

        @Override
        public void didDismissMoreApps() {
            Log.i(TAG, "MORE APPS DISMISSED");
            Toast.makeText(PartnerActivity.this, "Dismissed More Apps",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didCloseMoreApps() {
            Log.i(TAG, "MORE APPS CLOSED");
            Toast.makeText(PartnerActivity.this, "Closed More Apps",
                    Toast.LENGTH_SHORT).show();

            Intent activityChangeIntent = new Intent(PartnerActivity.this, FirstActivity.class);
            PartnerActivity.this.startActivity(activityChangeIntent);

        }

        @Override
        public void didClickMoreApps() {
            Log.i(TAG, "MORE APPS CLICKED");
            Toast.makeText(PartnerActivity.this, "Clicked More Apps",
                    Toast.LENGTH_SHORT).show();
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
