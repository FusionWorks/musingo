package iis.production.musingo.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dima on 2/4/14.
 */
public class PlaySongsTable {
    private String dbName = "musingo.db";

    public PlaySongsTable(Activity activity){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS play_songs " +
                "(ID integer primary key autoincrement, " +
                "PackageNr integer, " +
                "PackageName VARCHAR(255), " +
                "LevelNr integer UNIQUE, " +
                "LevelName VARCHAR(255), " +
                "BestResult integer, " +
                "GreenStar integer, " +
                "OrangeStar integer, " +
                "PinkStar integer);");
        db.close();
    }

    public void InsertIntoPlaySongsTable(Activity activity, Integer packageNr, String packageName, Integer levelNr, String levelName, Integer bestResult, Integer greenStar, Integer orangeStar, Integer pinkStar){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO play_songs " + "(PackageNr, PackageName, LevelNr, LevelName, BestResult, GreenStar, OrangeStar, PinkStar) VALUES (" + packageNr + ",'" + packageName + "'," + levelNr + ",'" + levelName + "'," + bestResult + "," + greenStar + "," + orangeStar + "," + pinkStar +");");
        db.close();
    }

    public void UpdateBestResultInPlaySongsTable(Activity activity, Integer newBestResult, Integer levelNr){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE play_songs SET BestResult = " + newBestResult + " WHERE LevelNr = " + levelNr);
        db.close();
    }

    public void UpdateStarInPlaySongsTable(Activity activity, Integer greenStar, Integer orangeStar, Integer pinkStar, Integer levelNr){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE play_songs SET GreenStar = " + greenStar + ", OrangeStar = "+ orangeStar + ", PinkStar = " + pinkStar + " WHERE LevelNr = " + levelNr);
        db.close();
    }

    public int GetBestResult(Activity activity, Integer levelNr){
        int bestResult = 0;
        try {
            SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery("SELECT BestResult FROM play_songs WHERE LevelNr = " + levelNr, null);
            int bestResultColumn = c.getColumnIndex("BestResult");
            c.moveToFirst();
            bestResult = c.getInt(bestResultColumn);
            db.close();
        }
        catch (Exception e){}
        return bestResult;
    }

    public int SumStar(Activity activity, Integer packageNr){
        int sumStar = 0;
        int column;
        Cursor cursor;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);

        cursor = db.rawQuery("SELECT sum(GreenStar) FROM play_songs WHERE PackageNr = " + packageNr, null);
        if(cursor.moveToFirst()) {
            sumStar = sumStar + cursor.getInt(0);
        }

        cursor = db.rawQuery("SELECT sum(OrangeStar) FROM play_songs WHERE PackageNr = " + packageNr, null);
        if(cursor.moveToFirst()) {
            sumStar = sumStar + cursor.getInt(0);
        }

        cursor = db.rawQuery("SELECT sum(PinkStar) FROM play_songs WHERE PackageNr = " + packageNr, null);
        if(cursor.moveToFirst()) {
            sumStar = sumStar + cursor.getInt(0);
        }

        db.close();
        return sumStar;
    }

    public void DeletePlaySongsTable(Activity activity){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS play_songs");
        db.close();
    }
}
