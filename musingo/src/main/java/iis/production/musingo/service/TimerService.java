package iis.production.musingo.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by dima on 2/26/14.
 */
public class TimerService extends Service{
    Activity activity;
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "settings";

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Log.d("Musingo", "onCreate timerService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Musingo", "onStartCommand");
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("Musingo", "timerservice onDestroy");
    }

    public void startTimer(){
        new CountDownTimer(900000, 1000) {

            public void onTick(long millisUntilFinished) {
//                Log.v("Musingo", "time : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.v("Musingo", "timer finish");
                mSettings.edit().putBoolean("timerService",false).commit();
            }
        }.start();
    }
}
