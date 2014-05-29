package com.zerofull800.basedataproccess.lib.data.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Created by NestorLuis on 1/4/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper ourInstance;

    private final String TAG = getClass().getName();

    /**
     * Database version code
     */
    private static final int DB_VERSION = 4;

    /**
     * Database Name
     */
    private static String DB_NAME;

    /**
     * Listener for db created or updated.
     */
    private IDatabaseCreated listener;

    /**
     * Main constructor
     * @param context Application Context
     * @param iDatabaseCreated Database event listener
     * @param DB_NAME Database name
     */
    public DatabaseHelper(Context context, IDatabaseCreated iDatabaseCreated, String DB_NAME) {
        super(context, DB_NAME, null, DB_VERSION);
        this.DB_NAME = DB_NAME;
        this.listener = iDatabaseCreated;
    }

    public static DatabaseHelper getInstance(Context context, IDatabaseCreated iDatabaseCreated, String DB_NAME) {
        if (ourInstance == null) {
            ourInstance = new DatabaseHelper(context, iDatabaseCreated, DB_NAME);
        }

        return ourInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            listener.OnDatabaseCreated(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            listener.OnDatabaseUpgraded(sqLiteDatabase,connectionSource,i,i2);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Interface for implementing model creation on Main Application start.
     */
    public static interface IDatabaseCreated {
        /**
         * On create database logistic
         * @param sqLiteDatabase SQLiteDatabase object
         * @param connectionSource ConnectionSource object
         */
        public void OnDatabaseCreated(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
        throws SQLException;

        /**
         * On upgrade database logistic
         * @param sqLiteDatabase SQLiteDatabase object
         * @param connectionSource ConnectionSource object
         * @param i
         * @param i2
         */
        public void OnDatabaseUpgraded(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2)
        throws SQLException;
    }
}
