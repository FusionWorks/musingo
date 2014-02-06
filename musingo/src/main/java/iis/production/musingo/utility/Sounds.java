package iis.production.musingo.utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import iis.production.musingo.R;

/**
 * Created by AGalkin on 2/5/14.
 */
public class Sounds {

    public static void soundCorrect(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.correct, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundWrong(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.wrong, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundButton(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.button, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 10, 10, 1, 0, 1f);
    }

    public static void soundBonus(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.bonus, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundCountdown(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.countdown, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundlose(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.lose_game, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundWin(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.win_game, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundFreeze(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_freeze, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundHelp(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_help, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundHint(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_hint, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundLonger(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_longerclip, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundNext(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_next, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundReplay(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_replay, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

    public static void soundSkip(Context context){
        SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int iTmp = sp.load(context, R.raw.powerup_skip, 1); // in 2nd param u have to pass your desire ringtone
        sp.play(iTmp, 1, 1, 0, 0, 1);
    }

}
