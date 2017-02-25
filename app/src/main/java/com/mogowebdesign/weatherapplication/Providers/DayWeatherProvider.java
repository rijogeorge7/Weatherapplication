package com.mogowebdesign.weatherapplication.Providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by rijogeorge on 2/22/17.
 */

public class DayWeatherProvider extends ContentProvider {

    private static final int WEATHERDAYLIST = 1;
    private static final int WEATHERDAYLISTALL = 2;
    private static final String WEATHERDAYLIST_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/com.mogowebdesign.weatherapplication.Model.Days";
    private static final UriMatcher URI_MATCHER ;

    private DayWeatherDatabase mDayWeatherDatabase;

    static {
        URI_MATCHER=new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DayWeatherProviderContract.AUTHORITY,"dayWeatherList",WEATHERDAYLIST);
        URI_MATCHER.addURI(DayWeatherProviderContract.AUTHORITY,"dayWeatherListAll",WEATHERDAYLISTALL);
    }

    @Override
    public boolean onCreate() {
        mDayWeatherDatabase=new DayWeatherDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(URI_MATCHER.match(uri)){
            case WEATHERDAYLIST:
                return WEATHERDAYLIST_CONTENT_TYPE;
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db=mDayWeatherDatabase.getWritableDatabase();
        long id = db.insert(DayWeatherProviderContract.DayWeatherDB.TABLE_NAME,null,contentValues);
        Uri itemUri = ContentUris.withAppendedId(uri, id);
        if(id>0)
        getContext().getContentResolver().notifyChange(itemUri, null);
        return itemUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] contentValues) {
        SQLiteDatabase db=mDayWeatherDatabase.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from day_weather",null);
        int dbsize=cursor.getCount();
        Log.d("db","db before "+String.valueOf(dbsize));
        int count = 0;
        cursor.close();
        try {
            db.beginTransaction();
            db.execSQL("delete from "+DayWeatherProviderContract.DayWeatherDB.TABLE_NAME);
            for(ContentValues values : contentValues) {
                Uri resultUri = doInsert(uri,values,db);
                if (resultUri != null) {
                    count++;
                }
                else {
                    count = 0;
                    throw new SQLException("Error in bulk insert");
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Cursor cursor2=db.rawQuery("select * from day_weather",null);
        int dbsize2=cursor2.getCount();
        Log.d("db","db after "+String.valueOf(dbsize2));
        cursor2.close();
        return count;
    }

    private Uri doInsert(Uri uri, ContentValues values, SQLiteDatabase database) {
        Uri result=null;
        long id = database.insert(DayWeatherProviderContract.DayWeatherDB.TABLE_NAME,null,values);
        if(id==-1)
            throw new SQLException("Error inserting data");
        result = Uri.withAppendedPath(uri,String.valueOf(id));
        return result;
    }

    class DayWeatherDatabase extends SQLiteOpenHelper {

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + DayWeatherProviderContract.DayWeatherDB.TABLE_NAME + " (" +
                        DayWeatherProviderContract.DayWeatherDB._ID + " INTEGER PRIMARY KEY," +
                        DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_DAY + " TEXT," +
                        DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MAX_TEMP + " INTEGER," +
                        DayWeatherProviderContract.DayWeatherDB.COLUMN_NAME_MIN_TEMP + " INTEGER)";

        private final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + DayWeatherProviderContract.DayWeatherDB.TABLE_NAME;

        public DayWeatherDatabase(Context context) {
            super(context, "name", null, 1);
            Log.d("db create stmt",DayWeatherDatabase.SQL_CREATE_TABLE);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
