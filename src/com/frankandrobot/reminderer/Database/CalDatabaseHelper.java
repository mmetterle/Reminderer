package com.frankandrobot.reminderer.Database;

import java.sql.Timestamp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.frankandrobot.reminderer.Database.Task;

public class CalDatabaseHelper extends SQLiteOpenHelper {

    static final String dbName = "CAL_DB";
    static final int dbVersion = 2;
    static final String colSeqNbr = "SEQ_NBR";
    static final String colDateTime = "EVENT DATE";
    static final String colTskName = "TASK_NAME";
    static final String colDuration = "DURATION";
    static final String colLocation = "LOCATION";

    // Task table name
    private static final String TASK_TABLE = "TASK_TABLE";

    // Super constructor that executes
    public CalDatabaseHelper(Context context, CursorFactory factory) {
	super(context, dbName, factory, dbVersion);
    }

    public void onCreate(SQLiteDatabase db) {
	// Create table with primary key (seq number, date/time) and columns
	db.execSQL("CREATE TABLE " + TASK_TABLE + " " + "(" + colSeqNbr
		+ " INTEGER NOT NULL, " + colDateTime + " DATETIME, "
		+ colTskName + " TEXT, " + colDuration + " TEXT, "
		+ colLocation + " TEXT, " + "PRIMARY KEY(" + colSeqNbr
		+ " AUTOINCREMENT, " + colDateTime + "))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// DROP table if any structural upgrades occur
	db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
	// Creates table again
	onCreate(db);
    }

    // Inserting single task
    public void addTask(Task task) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues cv = new ContentValues();
	String id = String.valueOf(task.getID());
	cv.put(colSeqNbr, 1);
	cv.put(colDateTime, id); // Event Date and Time
	cv.put(colTskName, task.getTaskName()); // Task Name
	cv.put(colDuration, task.getTaskDuration()); // Task Duration
	cv.put(colLocation, task.getTaskLocation()); // Task Location

	// Inserting Row
	db.insert(TASK_TABLE, null, cv);
	db.close(); // Closing database connection
    }

    // Updating single task
    public int UpdateTask(Task task) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues cv = new ContentValues();
	cv.put(colDateTime, task.getID());
	cv.put(colTskName, task.getTaskName());
	cv.put(colDuration, task.getTaskDuration());
	cv.put(colLocation, task.getTaskLocation());
	return db.update(TASK_TABLE, cv, colSeqNbr + " = ? ",
		new String[] { String.valueOf(task.getID()) });
    }

    // Deleting a single task
    public void DeleteTask(Task task) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TASK_TABLE, colDateTime + " = ? ",
		new String[] { String.valueOf(task.getID()) });
	db.close();
    }

    // Selecting a single task
    public Task getTaskByTS(Timestamp timestamp) {
	SQLiteDatabase db = this.getReadableDatabase();
	String[] columns = new String[] { colDateTime, colTskName, colDuration,
		colLocation };
	// db.query(tablename, columns, where clause, parameters of where
	// clause)
	Cursor cursor = db.query(TASK_TABLE, columns, colDateTime + " = ? ",
		new String[] { String.valueOf(timestamp) }, null, null, null,
		null);

	// if cursor isn't empty, move to first row
	if (cursor != null)
	    cursor.moveToFirst();

	// Retrieve index values from TASK_TABLE and store in task object
	Task task = new Task(Timestamp.valueOf(cursor.getString(0)),
		cursor.getString(1), cursor.getString(2), cursor.getString(3));
	// return task
	return task;
    }
}