package iis.production.musingo;

import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

/**
 * Created by AGalkin on 1/29/14.
 */
public class MusingoApp extends Application {

    private MixpanelAPI mixpanelAPI;
    static int button;
    static int bonus;
    static int correct;
    static int countdown;
    static int lose_game;
    static int powerup_freeze;
    static int powerup_help;
    static int powerup_hint;
    static int powerup_longerclip;
    static int powerup_next;
    static int powerup_replay;
    static int powerup_skip;
    static int win_game;
    static int wrong;

    public static float volume = 1;

    static SoundPool soundPool;

    public MixpanelAPI getMixpanelobj(){
        return mixpanelAPI;
    }

    public void setMixpanelobj(MixpanelAPI mixpanelAPI){
        this.mixpanelAPI = mixpanelAPI;
    }

    public void loadSongs(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        button = soundPool.load(this, R.raw.button, 1);
        bonus = soundPool.load(this, R.raw.bonus, 1);
        correct = soundPool.load(this, R.raw.correct, 1);
        countdown = soundPool.load(this, R.raw.countdown, 1);
        lose_game = soundPool.load(this, R.raw.lose_game, 1);
        powerup_freeze = soundPool.load(this, R.raw.powerup_freeze, 1);
        powerup_help = soundPool.load(this, R.raw.powerup_help, 1);
        powerup_hint = soundPool.load(this, R.raw.powerup_hint, 1);
        powerup_longerclip = soundPool.load(this, R.raw.powerup_longerclip, 1);
        powerup_next = soundPool.load(this, R.raw.powerup_next, 1);
        powerup_replay = soundPool.load(this, R.raw.powerup_replay, 1);
        powerup_skip = soundPool.load(this, R.raw.powerup_skip, 1);
        win_game = soundPool.load(this, R.raw.win_game, 1);
        wrong = soundPool.load(this, R.raw.wrong, 1);
    }

    public static void soundCorrect(){
        soundPool.play(correct, volume, volume, 0, 0, 1);
    }

    public static void soundWrong(){
        soundPool.play(wrong, volume, volume, 0, 0, 1);
    }


    public static void soundButton(){
        soundPool.play(button, volume, volume, 1, 0, 1f);
    }

    public static void soundBonus(){
        soundPool.play(bonus, volume, volume, 0, 0, 1);
    }

    public static void soundCountdown(){
        soundPool.play(countdown, volume, volume, 0, 0, 1);
    }

    public static void soundlose(){
        soundPool.play(lose_game, volume, volume, 0, 0, 1);
    }

    public static void soundWin(){
        soundPool.play(win_game, volume, volume, 0, 0, 1);
    }

    public static void soundFreeze(){
        soundPool.play(powerup_freeze, volume, volume, 0, 0, 1);
    }

    public static void soundHelp(){
        soundPool.play(powerup_help, volume, volume, 0, 0, 1);
    }

    public static void soundHint(){
        soundPool.play(powerup_hint, volume, volume, 0, 0, 1);
    }

    public static void soundLonger(){
        soundPool.play(powerup_longerclip, volume, volume, 0, 0, 1);
    }

    public static void soundNext(){
        soundPool.play(powerup_next, volume, volume, 0, 0, 1);
    }

    public static void soundReplay(){
        soundPool.play(powerup_replay, volume, volume, 0, 0, 1);
    }

    public static void soundSkip(){
        soundPool.play(powerup_skip, volume, volume, 0, 0, 1);
    }


}
