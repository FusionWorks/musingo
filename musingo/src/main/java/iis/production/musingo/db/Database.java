package iis.production.musingo.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dima on 2/4/14.
 */
public class Database {
    private SQLiteDatabase db;

    public Database(Activity activity, String dbName){
        db = activity.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
