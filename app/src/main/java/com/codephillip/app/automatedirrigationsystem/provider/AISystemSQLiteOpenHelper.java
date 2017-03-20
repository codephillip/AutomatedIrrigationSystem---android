package com.codephillip.app.automatedirrigationsystem.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.codephillip.app.automatedirrigationsystem.BuildConfig;
import com.codephillip.app.automatedirrigationsystem.provider.croptable.CroptableColumns;
import com.codephillip.app.automatedirrigationsystem.provider.metrictable.MetrictableColumns;

public class AISystemSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = AISystemSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "ais.db";
    private static final int DATABASE_VERSION = 1;
    private static AISystemSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final AISystemSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_CROPTABLE = "CREATE TABLE IF NOT EXISTS "
            + CroptableColumns.TABLE_NAME + " ( "
            + CroptableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CroptableColumns.NAME + " TEXT, "
            + CroptableColumns.OPTIMAL_WATER_LEVEL + " REAL, "
            + CroptableColumns.CROP_TYPE + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_METRICTABLE = "CREATE TABLE IF NOT EXISTS "
            + MetrictableColumns.TABLE_NAME + " ( "
            + MetrictableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MetrictableColumns.WATER_VOLUME + " REAL, "
            + MetrictableColumns.ISIRRIGATING + " INTEGER, "
            + MetrictableColumns.TIME + " TEXT, "
            + MetrictableColumns.DATE + " TEXT "
            + " );";

    // @formatter:on

    public static AISystemSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static AISystemSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static AISystemSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new AISystemSQLiteOpenHelper(context);
    }

    private AISystemSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new AISystemSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static AISystemSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new AISystemSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private AISystemSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new AISystemSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_CROPTABLE);
        db.execSQL(SQL_CREATE_TABLE_METRICTABLE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
