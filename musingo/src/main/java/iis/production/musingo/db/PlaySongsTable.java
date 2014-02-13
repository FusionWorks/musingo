package iis.production.musingo.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by dima on 2/4/14.
 */
public class PlaySongsTable {
    private String dbName = "musingo.db";
    private Activity activity;
    public PlaySongsTable(Activity activity){
        this.activity = activity;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS play_songs " +
                "(ID integer primary key autoincrement, " +
                "PackageName VARCHAR(255), " +
                "LevelNr integer UNIQUE, " +
                "LevelName VARCHAR(255), " +
                "BestResult integer, " +
                "CompleteStar integer, " +
                "BeatStar integer, " +
                "BoostStar integer);");
        db.close();
    }

    public void insertIntoPlaySongsTable(Integer packageNr, String packageName, Integer levelNr, String levelName, Integer bestResult, Integer completeStar, Integer beatStar, Integer boostStar){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO play_songs " + "(PackageName, LevelNr, LevelName, BestResult, CompleteStar, BeatStar, BoostStar) VALUES (" + packageName + "'," + levelNr + ",'" + levelName + "'," + bestResult + "," + completeStar + "," + beatStar + "," + boostStar +");");
        db.close();
    }

    public void updateBestResultInPlaySongsTable(Integer newBestResult, Integer levelNr){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE play_songs SET BestResult = " + newBestResult + " WHERE LevelNr = " + levelNr);
        db.close();
    }

    public void updateStarInPlaySongsTable(Boolean completeStar, Boolean beatStar, Boolean boostStar, Integer levelNr){
        int completeStarInt = (completeStar)? 1 : 0;
        int beatStarInt = (beatStar)? 1 : 0;
        int boostStarInt = (boostStar)? 1 : 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE play_songs SET CompleteStar = " + completeStarInt + ", BeatStar = "+ beatStarInt + ", BoostStar = " + boostStarInt + " WHERE LevelNr = " + levelNr);
        db.close();
    }

    public int getBestResult(Integer levelNr){
        int bestResult = 0;
            SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery("SELECT BestResult FROM play_songs WHERE LevelNr = " + levelNr, null);
            if(c.moveToFirst()) {
                bestResult = c.getInt(0);
            }
            db.close();
        return bestResult;
    }

    public int getSumStars(){
        int sumStar = 0;
        Cursor cursor;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);

        cursor = db.rawQuery("SELECT sum(CompleteStar) FROM play_songs", null);
        if(cursor.moveToFirst()) {
            sumStar = sumStar + cursor.getInt(0);
        }

        cursor = db.rawQuery("SELECT sum(BeatStar) FROM play_songs", null);
        if(cursor.moveToFirst()) {
            sumStar = sumStar + cursor.getInt(0);
        }

        cursor = db.rawQuery("SELECT sum(BoostStar) FROM play_songs", null);
        if(cursor.moveToFirst()) {
            sumStar = sumStar + cursor.getInt(0);
        }

        db.close();
        return sumStar;
    }

    public HashMap<String, Integer> getSongByLevelNr(Integer levelNr){
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM play_songs WHERE LevelNr = " + levelNr, null);
        if(c.moveToFirst()) {
            Log.v("Musingo", "cursor " + c);
            int completeStar = c.getInt((c.getColumnIndex("CompleteStar")));
            int beatStar = c.getInt((c.getColumnIndex("BeatStar")));
            int boostStar = c.getInt((c.getColumnIndex("BoostStar")));
            hash.put("completeStar", completeStar);
            hash.put("beatStar", beatStar);
            hash.put("boostStar", boostStar);
        }
        db.close();

        return hash;
    }

    public void deletePlaySongsTable(){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS play_songs");
        db.close();
    }
}
