package iis.production.musingo.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dima on 2/4/14.
 */
public class PlaySongTable {

    public void CreatePlaySongsTable(Activity activity, String dbName){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS play_songs " +
                "(ID integer primary key autoincrement, " +
                "PackageNr integer, " +
                "PackageName VARCHAR(255), " +
                "LevelNr integer, " +
                "LevelName VARCHAR(255), " +
                "BestResult integer, " +
                "GreenStar integer, " +
                "OrangeStar integer, " +
                "PinkStar integer);");
        db.close();
    }

    public void InsertIntoPlaySongsTable(Activity activity, String dbName, Integer packageNr, String packageName, Integer levelNr, String levelName, Integer bestResult, Integer greenStar, Integer orangeStar, Integer pinkStar){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO play_songs " + "(PackageNr, PackageName, LevelNr, LevelName, BestResult, GreenStar, OrangeStar, PinkStar) VALUES (" + packageNr + ",'" + packageName + "'," + levelNr + ",'" + levelName + "'," + bestResult + "," + greenStar + "," + orangeStar + "," + pinkStar +");");
        db.close();
    }

    public void UpdateBestResultInPlaySongsTable(Activity activity, String dbName, Integer newBestResult, Integer levelNr){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE play_songs SET BestResult = " + newBestResult + " WHERE LevelNr = " + levelNr);
        db.close();
    }

    public void UpdateStarInPlaySongsTable(Activity activity, String dbName, Integer greenStar, Integer orangeStar, Integer pinkStar, Integer levelNr){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE play_songs SET GreenStar = " + greenStar + ", OrangeStar = "+ orangeStar + ", PinkStar = " + pinkStar + " WHERE LevelNr = " + levelNr);
        db.close();
    }

    public int GetBestResult(Activity activity, String dbName, Integer levelNr){
        int bestResult = 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT BestResult FROM play_songs WHERE LevelNr = " + levelNr, null);
        int bestResultColumn = c.getColumnIndex("BestResult");
        c.moveToFirst();
        bestResult = c.getInt(bestResultColumn);
        db.close();
        return bestResult;
    }
}
