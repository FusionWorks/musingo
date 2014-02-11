package iis.production.musingo.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dima on 2/11/14.
 */
public class PackageTable {
    private String dbName = "musingo.db";
    private Activity activity;

    public PackageTable(Activity activity){
        this.activity = activity;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS package " +
                "(ID integer primary key autoincrement, " +
                "PackageName VARCHAR(255) UNIQUE, " +
                "Unlocked integer, " +
                "YourScore integer);");
        db.close();
    }

    public void insertIntoPackageTable(String packageName, boolean unlocked, int youScore){
        int unlockedInt = (unlocked)? 1 : 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO package " + "(PackageName, Unlocked, YourScore) VALUES ('" + packageName + "'," + unlockedInt + "," + youScore +");");
        db.close();
    }

    public void upadateScoreInPackageTable(int score, String packageName){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE package SET YourScore = " + score + " WHERE PackageName = " + packageName);
        db.close();
    }

    public boolean isUnlocked(String packageName){
        int unlock = 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT Unlocked FROM package WHERE PackageName = " + packageName, null);
        if(c.moveToFirst()) {
            unlock = c.getInt(0);
        }
        db.close();

        if (unlock == 1){
            return true;
        } else {
            return false;
        }
    }

    public int getScore(String packageName){
        int score = 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT YourScore FROM package WHERE PackageName = " + packageName, null);
        if(c.moveToFirst()) {
            score = c.getInt(0);
        }
        db.close();

        return score;
    }
}
