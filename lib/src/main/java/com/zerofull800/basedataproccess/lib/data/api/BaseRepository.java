package com.zerofull800.basedataproccess.lib.data.api;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.zerofull800.basedataproccess.lib.data.exception.DeleteDataException;
import com.zerofull800.basedataproccess.lib.data.exception.InsertDataException;
import com.zerofull800.basedataproccess.lib.data.exception.UpdateDataException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by NestorLuis on 1/4/14.
 */
public abstract class BaseRepository<T> {
    ///Constants
    protected final String TAG = getClass().getName();

    ///Objects.
    protected Dao<T, String> dao;
    protected DatabaseHelper db;

    /**
     * Base Constructor
     *
     * @param context App Context.
     */
    public BaseRepository(Context context, DatabaseHelper.IDatabaseCreated iDatabaseCreated, String DB_NAME) {
        db = DatabaseHelper.getInstance(context, iDatabaseCreated, DB_NAME);
    }

    /**
     * Create a new T object into Database.
     *
     * @param object object to persists into database.
     */
    public void create(T object) throws InsertDataException{
        try {
            dao.create(object);
        } catch (SQLException e) {
            throw new InsertDataException(e.getMessage());
        }
    }

    /**
     * Delete an existing object from database.
     *
     * @param object object to delete from database.
     */
    public void delete(T object) throws DeleteDataException{
        try {
            dao.delete(object);
        } catch (SQLException e) {
            throw new DeleteDataException(e.getMessage());
        }
    }

    /**
     * Update an existing object into database.
     *
     * @param object object to update from database.
     */
    public void update(T object) throws UpdateDataException {
        try {
            dao.update(object);
        } catch (SQLException e) {
            throw new UpdateDataException(e.getMessage());
        }
    }

    /**
     * Gets all rows of an object type from Database.
     *
     * @return A List of T object.
     */
    public List<T> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
