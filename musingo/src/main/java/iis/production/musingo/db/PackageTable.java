package iis.production.musingo.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
                "PackageUnlock integer, " +
                "YourScore integer);");
        db.close();
    }

    public void insertIntoPackageTable(String packageName, boolean unlocked, int youScore){
        int unlockedInt = (unlocked)? 1 : 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        try {
            db.execSQL("INSERT INTO package " + "(PackageName, PackageUnlock, YourScore) VALUES ('" + packageName + "'," + unlockedInt + "," + youScore +");");
        }
        catch (Exception e){}
        db.close();
    }

    public void updateScoreInPackageTable(int score, String packageName){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE package SET YourScore = " + score + " WHERE PackageName = " + packageName);
        db.close();
    }

    public void updateUnlocked(boolean isUnlocked, String packageName){
        int unlockedInt = (isUnlocked)? 1 : 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("UPDATE package SET PackageUnlock = " + unlockedInt + " WHERE PackageName = '" + packageName + "'");
        db.close();
    }

    public boolean isUnlocked(String packageName){
        int unlock = 0;
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT PackageUnlock FROM package WHERE PackageName = '" + packageName + "'", null);
        if(c.moveToFirst()) {
           unlock = c.getInt(0);
           Log.v("Musingo", "PackageUnlock: " + unlock);
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

    public void deletePackageTable(){
        SQLiteDatabase db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS package");
        db.close();
    }
}
